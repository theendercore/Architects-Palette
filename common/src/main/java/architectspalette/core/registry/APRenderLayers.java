package architectspalette.core.registry;

import architectspalette.core.platform.Services;
import net.minecraft.client.renderer.RenderType;

import static architectspalette.core.registry.APBlocks.*;

public class APRenderLayers {

    //"use json for it" yeah nice try forge. i know you won't take this out.
    //if you add dynamic render layers, then we'll talk.
    public static void setupRenderLayers() {
//        Services.REGISTRY.registerRenderLayer(APBlocksFG.ENTWINE_BARS.get(), RenderType.cutout());
//        Services.REGISTRY.registerRenderLayer(APBlocksFG.SUNMETAL_BARS.get(), RenderType.cutout());
//
//        Services.REGISTRY.registerRenderLayer(APBlocksFG.TWISTED_DOOR.get(), RenderType.cutout());
//        Services.REGISTRY.registerRenderLayer(APBlocksFG.TWISTED_TRAPDOOR.get(), RenderType.cutout());
//        Services.REGISTRY.registerRenderLayer(APBlocksFG.TWISTED_SAPLING.get(), RenderType.cutout());
//        Services.REGISTRY.registerRenderLayer(APBlocksFG.POTTED_TWISTED_SAPLING.get(), RenderType.cutout());
//
        Services.REGISTRY.setRenderLayer(APBlocks.REDSTONE_CAGE_LANTERN.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(APBlocks.GLOWSTONE_CAGE_LANTERN.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(APBlocks.ALGAL_CAGE_LANTERN.get(), RenderType.cutout());

        Services.REGISTRY.setRenderLayer(APBlocks.ACACIA_TOTEM_WING.get(), RenderType.cutout());

        Services.REGISTRY.setRenderLayer(HELIODOR_ROD.get(), RenderType.translucent());
        Services.REGISTRY.setRenderLayer(EKANITE_ROD.get(), RenderType.translucent());
        Services.REGISTRY.setRenderLayer(MONAZITE_ROD.get(), RenderType.translucent());

        Services.REGISTRY.setRenderLayer(NETHER_BRASS_FIRE.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_TORCH.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_WALL_TORCH.get(), RenderType.cutout());
//        Services.REGISTRY.registerRenderLayer(NETHER_BRASS_CHAIN.get(), RenderType.cutout());
//        Services.REGISTRY.registerRenderLayer(NETHER_BRASS_LANTERN.get(), RenderType.cutout());

//        //I'm lazy
//        for (RegistryObject<Block> obj : BLOCKS.getEntries()) {
//            if (obj.get() instanceof NubBlock block) {
//                Services.REGISTRY.registerRenderLayer(block, RenderType.cutout());
//            }
//        }
    }
}
