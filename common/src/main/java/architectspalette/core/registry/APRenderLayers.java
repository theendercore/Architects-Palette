package architectspalette.core.registry;

import architectspalette.content.blocks.NubBlock;
import architectspalette.core.platform.Services;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import static architectspalette.core.registry.APBlocks.*;

public class APRenderLayers {

    // (ender) Thanks for not doing a forgeism, this works better on multi-platform
    //"use json for it" yeah nice try forge. i know you won't take this out.
    //if you add dynamic render layers, then we'll talk.
    public static void setupRenderLayers() {
        Services.REGISTRY.setRenderLayer(ENTWINE_BARS, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(SUNMETAL_BARS, RenderType.cutout());

        Services.REGISTRY.setRenderLayer(TWISTED_DOOR , RenderType.cutout());
        Services.REGISTRY.setRenderLayer(TWISTED_TRAPDOOR , RenderType.cutout());
        Services.REGISTRY.setRenderLayer(TWISTED_SAPLING , RenderType.cutout());
        Services.REGISTRY.setRenderLayer(POTTED_TWISTED_SAPLING , RenderType.cutout());

        Services.REGISTRY.setRenderLayer(REDSTONE_CAGE_LANTERN, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(GLOWSTONE_CAGE_LANTERN, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(ALGAL_CAGE_LANTERN, RenderType.cutout());

        Services.REGISTRY.setRenderLayer(ACACIA_TOTEM_WING, RenderType.cutout());

        Services.REGISTRY.setRenderLayer(HELIODOR_ROD, RenderType.translucent());
        Services.REGISTRY.setRenderLayer(EKANITE_ROD, RenderType.translucent());
        Services.REGISTRY.setRenderLayer(MONAZITE_ROD, RenderType.translucent());

        Services.REGISTRY.setRenderLayer(NETHER_BRASS_FIRE, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_TORCH, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_WALL_TORCH, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_CHAIN, RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_LANTERN, RenderType.cutout());

        //I'm lazy
        // (ender) no you are a programmer.
        // Also is this even needed? Like it works fine without it.
        for (Block obj : Services.REGISTRY.getModBlocks()) {
            if (obj instanceof NubBlock block) {
                Services.REGISTRY.setRenderLayer(() -> block, RenderType.cutout());
            }
        }
    }
}
