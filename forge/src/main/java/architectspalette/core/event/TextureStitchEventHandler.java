package architectspalette.core.event;

import architectspalette.core.model.util.SpriteShift;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static architectspalette.core.APConstants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TextureStitchEventHandler {

//    @SubscribeEvent
//    public static void onTextureStitch(TextureStitchEvent.Pre event) {
//        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
//            event.addSprite(rl("block/sheet_metal_block_ct"));
//        }
//
//    }
    @SubscribeEvent
    public static void onTextureStitchPost(TextureStitchEvent event) {
        SpriteShift.onTexturesDoneStitching();
    }
}
