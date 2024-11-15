package architectspalette.core.model.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static architectspalette.core.APConstants.rl;

public class SpriteShift {

    private static final Map<String, SpriteShift> entries = new HashMap<>();
    private static Boolean areTexturesReady = false;

    protected ResourceLocation fromLocation;
    protected ResourceLocation toLocation;
    protected float uShift;
    protected float vShift;
    protected float vHeight;

    private SpriteShift(ResourceLocation from_block, ResourceLocation to_block) {
        fromLocation = from_block;
        toLocation = to_block;
        if (areTexturesReady) {
            init();
        }
    }

    public static SpriteShift getShift(ResourceLocation from_block, ResourceLocation to_block) {
        String key = from_block.toString() + "->" + to_block.toString();
        if (entries.containsKey(key)) {
            return entries.get(key);
        }
        SpriteShift shift = new SpriteShift(from_block, to_block);
        entries.put(key, shift);
        return shift;
    }

    public static SpriteShift getShift(String from_block, String to_block) {
        return getShift(rl(from_block), rl(to_block));
    }

    public static void onTexturesDoneStitching() {
        areTexturesReady = true;
        for (SpriteShift entry : entries.values()) {
            entry.init();
        }
    }

    private void init() {
        Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS);
        var from = atlas.apply(fromLocation);
        var to = atlas.apply(toLocation);

        uShift = to.getU0() - from.getU0();
        vShift = to.getV0() - from.getV0();
        vHeight = from.getV1() - from.getV0();
    }

    //Could optimize by saving this value. Hell, should probably just not save the sprites.
    // (ender) well I did that :)
    public float getUShift() {
        return uShift;
    }

    public float getVShift() {
        return vShift;
    }

    public float getVHeight() {
        return vHeight;
    }

}
