package architectspalette.core.registry;

import architectspalette.content.blocks.NubBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
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
    @SuppressWarnings("deprecation")
    public static void setupRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.ENTWINE_BARS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.SUNMETAL_BARS.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.TWISTED_DOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.TWISTED_TRAPDOOR.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.TWISTED_SAPLING.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.POTTED_TWISTED_SAPLING.get(), RenderType.cutout());
//
//        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.REDSTONE_CAGE_LANTERN.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.GLOWSTONE_CAGE_LANTERN.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.ALGAL_CAGE_LANTERN.get(), RenderType.cutout());
//
//        ItemBlockRenderTypes.setRenderLayer(APBlocksFG.ACACIA_TOTEM_WING.get(), RenderType.cutout());
//
//        ItemBlockRenderTypes.setRenderLayer(HELIODOR_ROD.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(EKANITE_ROD.get(), RenderType.translucent());
//        ItemBlockRenderTypes.setRenderLayer(MONAZITE_ROD.get(), RenderType.translucent());
//
//        ItemBlockRenderTypes.setRenderLayer(NETHER_BRASS_FIRE.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(NETHER_BRASS_TORCH.get(), RenderType.cutout());
//        ItemBlockRenderTypes.setRenderLayer(NETHER_BRASS_WALL_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NETHER_BRASS_CHAIN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(NETHER_BRASS_LANTERN.get(), RenderType.cutout());

        //I'm lazy
        for (RegistryObject<Block> obj : BLOCKS.getEntries()) {
            if (obj.get() instanceof NubBlock block) {
                ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
            }
        }
    }
}
