package architectspalette.core.mixin;

import architectspalette.core.api.CustomModelLoader;
import architectspalette.core.util.APCustomModelDataAccessor;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(BlockModel.class)
public abstract class BlockModelMixin implements APCustomModelDataAccessor {

    @Unique
    private CustomModelLoader.APCustomModelData ap$customModelData = null;

    @Override
    public CustomModelLoader.APCustomModelData ap$getCustomModelData() {
        return ap$customModelData;
    }

    @Override
    public void ap$setCustomModelData(CustomModelLoader.APCustomModelData ap$customModelData) {
        this.ap$customModelData = ap$customModelData;
    }

    @Inject(method = "bake(Lnet/minecraft/client/resources/model/ModelBaker;Ljava/util/function/Function;Lnet/minecraft/client/resources/model/ModelState;)Lnet/minecraft/client/resources/model/BakedModel;", at = @At("RETURN"), cancellable = true)
    void ap$customBake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, CallbackInfoReturnable<BakedModel> cir) {
        if (ap$customModelData != null) {
            var loader = CustomModelLoader.getLoader(ap$customModelData.getLoader());
            if (loader != null)
                cir.setReturnValue(loader.bake(modelBaker, spriteGetter, modelState, cir.getReturnValue(), (BlockModel) (Object) this));
        }
    }
}
