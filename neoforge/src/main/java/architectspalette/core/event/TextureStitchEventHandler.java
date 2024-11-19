package architectspalette.core.event;

import architectspalette.core.model.util.SpriteShift;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.TextureAtlasStitchedEvent;

public class TextureStitchEventHandler {
    @SubscribeEvent
    public static void onTextureStitchPost(TextureAtlasStitchedEvent event) {
        if (event.getAtlas().location() == InventoryMenu.BLOCK_ATLAS) {
            SpriteShift.onTexturesDoneStitching();
        }
    }
}
