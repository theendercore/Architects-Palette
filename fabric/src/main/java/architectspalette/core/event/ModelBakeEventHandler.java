package architectspalette.core.event;

import architectspalette.core.model.BoardModel;
import architectspalette.core.model.SheetMetalModel;
import architectspalette.core.model.util.SpriteShift;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APBlocks;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelModifier;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModelBakeEventHandler {
    public static void init() {
        ModelLoadingPlugin.register(ctx -> ctx.modifyModelAfterBake().register(new ModelSwapper()));
    }

    // Convenience function for EveryCompat. Sets up the board model and the Sprite Shift
    // (ender) I have no idea if this works
    public static void registerBoardModel(Supplier<Block> supplier, ResourceLocation baseBoardBlock) {
        var inBlockFolder = baseBoardBlock.withPrefix("block/");
        var odd = inBlockFolder.withSuffix("_odd");
        ModelSwapper.swapBlockModel(supplier, model -> new BoardModel(model, SpriteShift.getShift(inBlockFolder, odd)));
    }

    // (Ender) Stolen from AP Fabric
    public static class ModelSwapper implements ModelModifier.AfterBake {
        private static final HashMap<ModelResourceLocation, Function<BakedModel, BakedModel>> customBlockModels = new HashMap<>();

        public static void swapBlockModel(Supplier<Block> block, Function<BakedModel, BakedModel> oldToNewFunction) {
            for (ModelResourceLocation modelId : getAllBlockStateModelLocations(block)) {
                customBlockModels.put(modelId, oldToNewFunction);
            }
        }

        private static List<ModelResourceLocation> getAllBlockStateModelLocations(Supplier<Block> block) {
            List<ModelResourceLocation> models = new ArrayList<>();
            ResourceLocation blockId = Services.REGISTRY.getId(block);
            if (blockId == null) throw new IllegalStateException("Block ID is null");
            block.get().getStateDefinition().getPossibleStates().forEach(state ->
                    models.add(BlockModelShaper.stateToModelLocation(blockId, state)));
            return models;
        }

        @Override
        public BakedModel modifyModelAfterBake(BakedModel model, Context context) {
            if (customBlockModels.isEmpty())
                this.collectSwaps();

            ModelResourceLocation id = context.topLevelId();
            if (id instanceof ModelResourceLocation && customBlockModels.containsKey(id)) {
                return customBlockModels.get(id).apply(model);
            }
            return model;
        }

        public void collectSwaps() {
            APBlocks.SHEET_METAL.forEach((n) -> swapBlockModel(n, model -> new SheetMetalModel(model, SpriteShift.getShift("block/sheet_metal_block", "block/sheet_metal_block_ct"))));
         /*   APBlocks.boards.forEach((b) ->
                    b.forEach((n) -> swapBlockModel(n, model -> new BoardModel(model, SpriteShift.getShift("block/" + b.getName(), "block/" + b.getName() + "_odd"))))
            );*/
        }
    }
}
