package architectspalette.core.mixin;

import architectspalette.core.model.util.SpriteShift;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin {
    @Shadow @Final private ResourceLocation location;

    @Inject(method = "upload", at = @At("TAIL"))
    void ap$switchTextures(SpriteLoader.Preparations preparations, CallbackInfo ci) {
        if (this.location == InventoryMenu.BLOCK_ATLAS) SpriteShift.onTexturesDoneStitching();
    }
}
