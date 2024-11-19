package architectspalette.core.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;

public class WitheredBoneLootModifier extends LootModifier {
    public static final Supplier<MapCodec<WitheredBoneLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
            .and(
                    inst.group(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("replaces").forGetter((m) -> m.replacedItem),
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("bone").forGetter((m) -> m.boneItem)
                    )
            )
            .apply(inst, WitheredBoneLootModifier::new)));
    private final Item replacedItem;
    private final Item boneItem;

    protected WitheredBoneLootModifier(LootItemCondition[] conditionsIn, Item replacedItem, Item boneItem) {
        super(conditionsIn);
        this.replacedItem = replacedItem;
        this.boneItem = boneItem;
    }

    // (ender) I put this here since there is no reason to make a file if its only one condition
    public static void register(final IEventBus modBus) {
        var LOOT = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);
        LOOT.register("wither_skeleton_bones", WitheredBoneLootModifier.CODEC);
        LOOT.register(modBus);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Entity t = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (t == null) return generatedLoot;
        if (t instanceof WitherSkeleton) {
            int amountOfBones = 0;
            for (ItemStack i : generatedLoot) {
                //check if item is the item to replace, take note of how many there are
                if (i.getItem() == replacedItem) {
                    amountOfBones += i.getCount();
                }
            }
            generatedLoot.removeIf(i -> i.getItem() == replacedItem);
            generatedLoot.add(new ItemStack(boneItem, amountOfBones));
        }
        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
