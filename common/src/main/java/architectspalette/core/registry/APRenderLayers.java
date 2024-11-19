package architectspalette.core.registry;

import architectspalette.content.blocks.NubBlock;
import architectspalette.core.platform.Services;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

import static architectspalette.core.registry.APBlocks.*;

public class APRenderLayers {

    //"use json for it" yeah nice try forge. i know you won't take this out.
    //if you add dynamic render layers, then we'll talk.
    // (ender) Thanks for not doing a forgeism, this works better on multi-platform
    public static void setupRenderLayers() {
        Stream.of(
                ENTWINE_BARS, SUNMETAL_BARS,
                TWISTED_DOOR, TWISTED_TRAPDOOR, TWISTED_SAPLING, POTTED_TWISTED_SAPLING,
                REDSTONE_CAGE_LANTERN, GLOWSTONE_CAGE_LANTERN, ALGAL_CAGE_LANTERN,
                ACACIA_TOTEM_WING,
                NETHER_BRASS_FIRE, NETHER_BRASS_TORCH, NETHER_BRASS_WALL_TORCH, NETHER_BRASS_CHAIN, NETHER_BRASS_LANTERN
        ).forEach((it) -> Services.REGISTRY.setRenderLayer(it, RenderType.cutout()));

        Stream.of(HELIODOR_ROD,EKANITE_ROD, MONAZITE_ROD)
                .forEach((it) -> Services.REGISTRY.setRenderLayer(it, RenderType.translucent()));

        //I'm lazy
        // (ender) no you are a programmer.
        for (Block obj : Services.REGISTRY.getModBlocks()) {
            if (obj instanceof NubBlock block) {
                Services.REGISTRY.setRenderLayer(() -> block, RenderType.cutout());
            }
        }
    }
}
