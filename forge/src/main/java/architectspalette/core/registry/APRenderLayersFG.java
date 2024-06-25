package architectspalette.core.registry;

import architectspalette.content.blocks.NubBlock;
import architectspalette.core.platform.Services;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import static architectspalette.core.platform.ForgeRegistryHelper.BLOCKS;
import static architectspalette.core.registry.APBlocksFG.*;

@OnlyIn(Dist.CLIENT)
public class APRenderLayersFG {

    //"use json for it" yeah nice try forge. i know you won't take this out.
    //if you add dynamic render layers, then we'll talk.
    public static void setupRenderLayers() {
        Services.REGISTRY.setRenderLayer(APBlocksFG.ENTWINE_BARS.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(APBlocksFG.SUNMETAL_BARS.get(), RenderType.cutout());

        Services.REGISTRY.setRenderLayer(APBlocksFG.TWISTED_DOOR.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(APBlocksFG.TWISTED_TRAPDOOR.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(APBlocksFG.TWISTED_SAPLING.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(APBlocksFG.POTTED_TWISTED_SAPLING.get(), RenderType.cutout());
//
//        Services.REGISTRY.setRenderLayer(APBlocksFG.REDSTONE_CAGE_LANTERN.get(), RenderType.cutout());
//        Services.REGISTRY.setRenderLayer(APBlocksFG.GLOWSTONE_CAGE_LANTERN.get(), RenderType.cutout());
//        Services.REGISTRY.setRenderLayer(APBlocksFG.ALGAL_CAGE_LANTERN.get(), RenderType.cutout());
//
//        Services.REGISTRY.setRenderLayer(APBlocksFG.ACACIA_TOTEM_WING.get(), RenderType.cutout());
//
//        Services.REGISTRY.setRenderLayer(HELIODOR_ROD.get(), RenderType.translucent());
//        Services.REGISTRY.setRenderLayer(EKANITE_ROD.get(), RenderType.translucent());
//        Services.REGISTRY.setRenderLayer(MONAZITE_ROD.get(), RenderType.translucent());
//
//        Services.REGISTRY.setRenderLayer(NETHER_BRASS_FIRE.get(), RenderType.cutout());
//        Services.REGISTRY.setRenderLayer(NETHER_BRASS_TORCH.get(), RenderType.cutout());
//        Services.REGISTRY.setRenderLayer(NETHER_BRASS_WALL_TORCH.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_CHAIN.get(), RenderType.cutout());
        Services.REGISTRY.setRenderLayer(NETHER_BRASS_LANTERN.get(), RenderType.cutout());

        //I'm lazy
        for (RegistryObject<Block> obj : BLOCKS.getEntries()) {
            if (obj.get() instanceof NubBlock block) {
                Services.REGISTRY.setRenderLayer(block, RenderType.cutout());
            }
        }
    }
}
