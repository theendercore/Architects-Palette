package architectspalette.core.datagen;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.integration.advancement.CarveTotemTrigger;
import architectspalette.core.registry.APBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;


public class APAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    private static MutableComponent getTranslationKey(String key, Object... args) {
        return Component.translatable(ArchitectsPalette.MOD_ID + "." + key, args);
    }

    private ResourceLocation getNameId(String id) {
        return ArchitectsPalette.rl(id);
    }

    // Not even gonna skirt around the fact that I straight up copy pasted this part. vectorwing seriously thought of everything.
    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
            getTranslationKey("advancement." + name),
            getTranslationKey("advancement." + name + ".desc"),
            null, frame, showToast, announceToChat, hidden);
    }

    protected AdvancementHolder buyAdvancement(AdvancementHolder parent, ItemLike display, String name, String item, Consumer<AdvancementHolder> advancementConsumer) {
        return getAdvancement(parent, display, name, AdvancementType.TASK, true, false, false)
                .addCriterion(item, InventoryChangeTrigger.TriggerInstance.hasItems(display))
                .save(advancementConsumer, getNameId("main/" + name));
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> advancementConsumer, ExistingFileHelper existingFileHelper) {
        AdvancementHolder architectsPalette = Advancement.Builder.advancement()
                .display(APBlocks.CHISELED_ABYSSALINE_BRICKS.get().asItem(),
                        getTranslationKey("advancement.root"),
                        getTranslationKey("advancement.root.desc"),
                        ResourceLocation.parse("architects_palette:textures/block/myonite_bricks.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("craftingtable", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(advancementConsumer, getNameId("main/root"));

        AdvancementHolder totemCarving = getAdvancement(architectsPalette, APBlocks.PLACID_ACACIA_TOTEM.get().asItem(), "totem_carving", AdvancementType.TASK, true ,true, false)
                .addCriterion("carve_totem", CarveTotemTrigger.TriggerInstance.simple())
                .save(advancementConsumer, getNameId("main/totem_carving") );

        AdvancementHolder whatACatch = getAdvancement(architectsPalette, APBlocks.COD_LOG.get().asItem(), "buy_fish_block", AdvancementType.TASK, true, false, false)
                .addCriterion("cod_log", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.COD_LOG.get().asItem()))
                .addCriterion("salmon_log", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.SALMON_LOG.get().asItem()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(advancementConsumer, getNameId("main/buy_fish_block"));

        AdvancementHolder buyPipes = buyAdvancement(architectsPalette, APBlocks.PIPE.get().asItem(), "buy_pipe", "pipe", advancementConsumer);
        AdvancementHolder buyEntrails = buyAdvancement(architectsPalette, APBlocks.ENTRAILS.get().asItem(), "buy_entrails", "entrails", advancementConsumer);
        AdvancementHolder placeEntrails = getAdvancement(buyEntrails, APBlocks.ENTRAILS.get().asItem(), "place_entrails", AdvancementType.TASK, true, true, true)
                .addCriterion("entrails", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(APBlocks.ENTRAILS.get()))
                .save(advancementConsumer, getNameId("main/place_entrails"));

        AdvancementHolder buyPlating = buyAdvancement(architectsPalette, APBlocks.PLATING_BLOCK.get().asItem(), "buy_plating", "plating", advancementConsumer);
        AdvancementHolder buySpool = buyAdvancement(architectsPalette, APBlocks.SPOOL.get().asItem(), "buy_spool", "spool", advancementConsumer);

        AdvancementHolder buyCelestialStone = getAdvancement(architectsPalette, APBlocks.SUNSTONE.get().asItem(), "buy_celestial_stone", AdvancementType.TASK, true, false, false)
                .addCriterion("moonstone", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.MOONSTONE.get().asItem()))
                .addCriterion("sunstone", InventoryChangeTrigger.TriggerInstance.hasItems(APBlocks.SUNSTONE.get().asItem()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(advancementConsumer, getNameId("main/buy_celestial_stone"));

        AdvancementHolder getWarpstone = buyAdvancement(architectsPalette, APBlocks.WARPSTONE.get().asItem(), "find_warpstone", "warpstone", advancementConsumer);
        AdvancementHolder getTwistedSapling = buyAdvancement(getWarpstone, APBlocks.TWISTED_SAPLING.get().asItem(), "find_twisted_sapling", "sapling", advancementConsumer);

    }
}