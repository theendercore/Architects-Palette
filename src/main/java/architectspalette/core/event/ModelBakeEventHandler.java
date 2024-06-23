package architectspalette.core.event;

import architectspalette.core.ArchitectsPalette;
import architectspalette.core.model.BoardModel;
import architectspalette.core.model.HazardModel;
import architectspalette.core.model.SheetMetalModel;
import architectspalette.core.model.TileModel;
import architectspalette.core.model.util.SpriteShift;
import architectspalette.core.registry.APBlocks;
import architectspalette.core.registry.util.BlockNode;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ArchitectsPalette.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelBakeEventHandler {

    private static final Map<Entry, Function<BakedModel, ? extends BakedModel>> customBlockModels = new HashMap<>();

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
//
//        SpriteShift birchShift = new SpriteShift(rl("block/birch_boards"), rl("block/birch_boards_odd"));
//        register(APBlocks.BIRCH_BOARDS, model -> new BoardModel(model, birchShift));

        //Note; Not all model swaps are registered here.
        register(APBlocks.UNOBTANIUM_BLOCK, TileModel::new);
        register(APBlocks.HAZARD_BLOCK.getObject(), HazardModel::new);
        register(APBlocks.SHEET_METAL.getObject(), model -> new SheetMetalModel(model, SpriteShift.getShift("block/sheet_metal_block", "block/sheet_metal_block_ct")));
        register(APBlocks.SHEET_METAL.getChild(BlockNode.BlockType.WALL), model -> new SheetMetalModel(model, SpriteShift.getShift("block/sheet_metal_block", "block/sheet_metal_block_ct")));

        for (BlockNode board : APBlocks.boards) {
            var wall = board.getChild(BlockNode.BlockType.WALL);
            var shift = SpriteShift.getShift("block/" + board.getName(), "block/" + board.getName() + "_odd");
            register(wall, model -> new BoardModel(model, shift));
        }
        //SpriteShift.onTexturesDoneStitching();

        customBlockModels.forEach((entry, factory) -> swapModels(modelRegistry, getAllBlockStateModelLocations(entry), factory));

    }

    // Convenience function for EveryCompat. Sets up the board model and the Sprite Shift
    @SuppressWarnings("unused")
    public static void registerBoardModel(Supplier<Block> supplier, ResourceLocation blockToRegister, ResourceLocation baseBoardBlock) {
        var inBlockFolder = ResourceLocation.fromNamespaceAndPath(baseBoardBlock.getNamespace(),"block/" + baseBoardBlock.getPath());
        var odd = ResourceLocation.fromNamespaceAndPath(inBlockFolder.getNamespace(), inBlockFolder.getPath() + "_odd");
        register(supplier, blockToRegister, model -> new BoardModel(model, SpriteShift.getShift(inBlockFolder, odd)));
    }

    private static <T extends BakedModel> void swapModels(Map<ResourceLocation, BakedModel> modelRegistry, List<ModelResourceLocation> locations, Function<BakedModel, T> modelFactory) {
        locations.forEach( location -> swapModels(modelRegistry, location, modelFactory));
    }

    private static <T extends BakedModel> void swapModels(Map<ResourceLocation, BakedModel> modelRegistry, ModelResourceLocation location, Function<BakedModel, T> modelFactory) {
        modelRegistry.put(location, modelFactory.apply(modelRegistry.get(location)));
    }

    private static List<ModelResourceLocation> getAllBlockStateModelLocations(Entry entry) {
        return getAllBlockStateModelLocations(entry.getBlock(), entry.getResourceLocation());
    }

    private static List<ModelResourceLocation> getAllBlockStateModelLocations(Block block, ResourceLocation resourceLocation) {
        List<ModelResourceLocation> models = new ArrayList<>();
        block.getStateDefinition().getPossibleStates().forEach(state -> {
            models.add(BlockModelShaper.stateToModelLocation(resourceLocation, state));
        });
        return models;
    }

    public static <T extends BakedModel> void register(RegistryObject<? extends Block> registryObject, Function<BakedModel, T> modelFactory) {
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
