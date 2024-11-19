package architectspalette.core.event;

import architectspalette.core.model.BoardModel;
import architectspalette.core.model.SheetMetalModel;
import architectspalette.core.model.util.SpriteShift;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static architectspalette.core.registry.APBlocks.SHEET_METAL;

public class ModelBakeEventHandler {

    private static final Map<Entry, Function<BakedModel, ? extends BakedModel>> customBlockModels = new HashMap<>();

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> modelRegistry = event.getModels();
        // (ender) Not all models are handled here, there are also data-driven ones Look for WrappedModelLoader
        register((DeferredHolder<Block, ? extends Block>) SHEET_METAL.getObject(), model -> new SheetMetalModel(model, SpriteShift.getShift("block/sheet_metal_block", "block/sheet_metal_block_ct")));
        register((DeferredHolder<Block, ? extends Block>) SHEET_METAL.getChild(BlockNode.BlockType.WALL), model -> new SheetMetalModel(model, SpriteShift.getShift("block/sheet_metal_block", "block/sheet_metal_block_ct")));

        customBlockModels.forEach((entry, factory) -> swapModels(modelRegistry, getAllBlockStateModelLocations(entry), factory));
    }

    // Convenience function for EveryCompat. Sets up the board model and the Sprite Shift
    @Deprecated()// (ender) Use the wrapped models instead please
    public static void registerBoardModel(Supplier<Block> supplier, ResourceLocation blockToRegister, ResourceLocation baseBoardBlock) {
        var inBlockFolder = baseBoardBlock.withPrefix("block/");
        var odd = inBlockFolder.withSuffix("_odd");
        register(supplier, blockToRegister, model -> new BoardModel(model, SpriteShift.getShift(inBlockFolder, odd)));
    }

    private static <T extends BakedModel> void swapModels(Map<ModelResourceLocation, BakedModel> modelRegistry, List<ModelResourceLocation> locations, Function<BakedModel, T> modelFactory) {
        locations.forEach(location -> modelRegistry.put(location, modelFactory.apply(modelRegistry.get(location))));
    }

    private static List<ModelResourceLocation> getAllBlockStateModelLocations(Entry entry) {
        return getAllBlockStateModelLocations(entry.getBlock(), entry.getResourceLocation());
    }

    private static List<ModelResourceLocation> getAllBlockStateModelLocations(Block block, ResourceLocation resourceLocation) {
        List<ModelResourceLocation> models = new ArrayList<>();
        block.getStateDefinition().getPossibleStates().forEach(state -> models.add(BlockModelShaper.stateToModelLocation(resourceLocation, state)));
        return models;
    }

    public static <T extends BakedModel> void register(DeferredHolder<Block, ? extends Block> registryObject, Function<BakedModel, T> modelFactory) {
        register(registryObject, registryObject.getId(), modelFactory);
    }

    public static <T extends BakedModel> void register(Supplier<? extends Block> blockSupplier, ResourceLocation resourceLocation, Function<BakedModel, T> modelFactory) {
        customBlockModels.put(new Entry(resourceLocation, blockSupplier), modelFactory);
    }

    private static class Entry {
        private final ResourceLocation resourceLocation;
        private final Supplier<? extends Block> block;

        public Entry(ResourceLocation resourceLocation, Supplier<? extends Block> block) {
            this.resourceLocation = resourceLocation;
            this.block = block;
        }

        public Block getBlock() {
            return block.get();
        }

        public ResourceLocation getResourceLocation() {
            return resourceLocation;
        }
    }

}
