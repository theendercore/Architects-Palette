package architectspalette.core.datagen;

import architectspalette.content.blocks.TotemWingBlock;
import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.content.blocks.abyssaline.AbyssalineBlock;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

public class APLootTablesProvider extends FabricBlockLootTableProvider {
    public APLootTablesProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        //(ender) this got skipped somehow, idfk
        this.add((Block) APBlocks.ACACIA_TOTEM_WING.get(), this.createSingleItemTable(APBlocks.ACACIA_TOTEM_WING.get().asItem()));
        LOGGER.info("Generated Acacia Totem Wing {}", APBlocks.ACACIA_TOTEM_WING.get().getLootTable());
        LOGGER.info("Generated X {}", APBlocks.PACKED_ICE_PILLAR.get().getLootTable());

        // (ender) do everything first
        Services.REGISTRY.getModBlocks().forEach(this::processBlock);


        // (ender) then override special cases
        this.add((Block) APBlocks.NETHER_BRASS_FIRE.get(), noDrop());

        APBlocks.POLISHED_PACKED_ICE.forEach(this::dropWhenSilkTouch);
        this.dropWhenSilkTouch(APBlocks.CHISELED_PACKED_ICE.get());
        this.dropWhenSilkTouch(APBlocks.PACKED_ICE_PILLAR.get());

        this.dropPottedContents(APBlocks.POTTED_TWISTED_SAPLING.get());
        this.add(APBlocks.TWISTED_DOOR.get(), this::createDoorTable);
        this.add(APBlocks.TWISTED_LEAVES.get(), (leaves) -> this.createLeavesDrops(leaves, APBlocks.TWISTED_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(APBlocks.CHISELED_ABYSSALINE_BRICKS.get(), this::createChiseledAbyssaline);
        this.add(APBlocks.CHISELED_HADALINE_BRICKS.get(), this::createChiseledAbyssaline);

    }

    private void processBlock(Block block) {
        if (block instanceof TotemWingBlock || block instanceof FireBlock) return;

        if (block instanceof SlabBlock || block instanceof VerticalSlabBlock) slab(block);
        else this.dropSelf(block);
    }

    // (ender) I will leave this here but its really not needed unless you want to add more silk touch like things
   /* private void processStoneBlockSet(StoneBlockSet stoneBlockSet) {
        stoneBlockSet.forEachPart((part, block) -> {
            switch (part) {
                case SLAB, VERTICAL_SLAB -> slab(block);
                default -> this.dropSelf(block);
            }
        });
    }


    private void processBlockNode(BlockNode node) {
        node.forEach((n) -> {
            //Todo: Silk Touch Flag
            var block = n.get();
            switch (n.type) {
                case SLAB, VERTICAL_SLAB -> slab(block);
                default -> this.dropSelf(block);
            }
        });
    }*/

    private void slab(Block block) {
        this.add(block, createSlabItemTable(block));
    }

    public final LootTable.Builder createChiseledAbyssaline(Block block) {
        // (ender) this looks ugly, I don't like java
        // and don't say just make it one line, I cant read that at all
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                block,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block))
                        )
                )
                .withPool(
                        this.applyExplosionCondition(
                                Items.HEART_OF_THE_SEA,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.HEART_OF_THE_SEA))
                        ).conditionally(
                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
                                        StatePropertiesPredicate.Builder.properties().hasProperty(AbyssalineBlock.CHARGED, true)
                                ).build()
                        )
                );
    }
}