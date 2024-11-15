package architectspalette.core.datagen;

import architectspalette.core.integration.advancement.CarveTotemTrigger;
import architectspalette.core.registry.APBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.rl;

public class APAdvancementProvider extends FabricAdvancementProvider {
    public APAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    private static MutableComponent getTranslationKey(String key, Object... args) {
        return Component.translatable(MOD_ID + "." + key, args);
    }

    private String named(String id) {
        return rl(id).toString();
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder architectsPalette = Advancement.Builder.advancement()
                .display(APBlocks.CHISELED_ABYSSALINE_BRICKS.get().asItem(),
                        getTranslationKey("advancement.root"),
                        getTranslationKey("advancement.root.desc"),
                        rl("textures/block/myonite_bricks.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("craftingtable", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, named("main/root"));

        AdvancementHolder totemCarving = getAdvancement(architectsPalette, APBlocks.PLACID_ACACIA_TOTEM.get().asItem(), "totem_carving", AdvancementType.TASK, true ,true, false)
                .addCriterion("carve_totem", CarveTotemTrigger.TriggerInstance.simple())
                .save(consumer, named("main/totem_carving") );

        AdvancementHolder whatACatch = getAdvancement(architectsPalette, APBlocks.COD_LOG.get().asItem(), "buy_fish_block", AdvancementType.TASK, true, false, false)
                .addCriterion("cod_log", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.COD_LOG.get().asItem()))
                .addCriterion("salmon_log", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.SALMON_LOG.get().asItem()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, named("main/buy_fish_block"));

        AdvancementHolder buyPipes = buyAdvancement(architectsPalette, APBlocks.PIPE.get().asItem(), "buy_pipe", "pipe", consumer);
        AdvancementHolder buyEntrails = buyAdvancement(architectsPalette, APBlocks.ENTRAILS.get().asItem(), "buy_entrails", "entrails", consumer);
        AdvancementHolder placeEntrails = getAdvancement(buyEntrails, APBlocks.ENTRAILS.get().asItem(), "place_entrails", AdvancementType.TASK, true, true, true)
                .addCriterion("entrails", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(APBlocks.ENTRAILS.get()))
                .save(consumer, named("main/place_entrails"));

        AdvancementHolder buyPlating = buyAdvancement(architectsPalette, APBlocks.PLATING_BLOCK.get().asItem(), "buy_plating", "plating", consumer);
        AdvancementHolder buySpool = buyAdvancement(architectsPalette, APBlocks.SPOOL.get().asItem(), "buy_spool", "spool", consumer);

        AdvancementHolder buyCelestialStone = getAdvancement(architectsPalette, APBlocks.SUNSTONE.get().asItem(), "buy_celestial_stone", AdvancementType.TASK, true, false, false)
                .addCriterion("moonstone", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.MOONSTONE.get().asItem()))
                .addCriterion("sunstone", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.SUNSTONE.get().asItem()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, named("main/buy_celestial_stone"));

        AdvancementHolder getWarpstone = buyAdvancement(architectsPalette, APBlocks.WARPSTONE.get().asItem(), "find_warpstone", "warpstone", consumer);
        AdvancementHolder getTwistedSapling = buyAdvancement(getWarpstone, APBlocks.TWISTED_SAPLING.get().asItem(), "find_twisted_sapling", "sapling", consumer);


    }


    // (ender) don't worry this is still like 90% the stolen code. since I just works soo well.
    // Not even gonna skirt around the fact that I straight up copy pasted this part. vectorwing seriously thought of everything.
    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                getTranslationKey("advancement." + name),
                getTranslationKey("advancement." + name + ".desc"),
                null, frame, showToast, announceToChat, hidden);
    }

    protected AdvancementHolder buyAdvancement(AdvancementHolder parent, ItemLike display, String name, String item, Consumer<AdvancementHolder> consumer) {
        return getAdvancement(parent, display, name, AdvancementType.TASK, true, false, false)
                .addCriterion(item, InventoryChangeTrigger.TriggerInstance.hasItems(display))
                .save(consumer, named("main/" + name));
    }
}
