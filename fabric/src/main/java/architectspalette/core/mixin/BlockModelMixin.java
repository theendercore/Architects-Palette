package architectspalette.core.mixin;

import architectspalette.core.api.APCustomModelDataAccessor;
import architectspalette.core.api.CustomModelLoader;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(BlockModel.class)
public abstract class BlockModelMixin implements APCustomModelDataAccessor {

    @Unique
    private CustomModelLoader.APCustomModelData architects_palette$customModelData = null;

    @Override
    public CustomModelLoader.APCustomModelData architects_palette$getCustomModelData() {
        return architects_palette$customModelData;
    }

    @Override
    public void architects_palette$setCustomModelData(CustomModelLoader.APCustomModelData customModelData) {
        this.architects_palette$customModelData = customModelData;
    }

    @ModifyReturnValue(method = "bake(Lnet/minecraft/client/resources/model/ModelBaker;Ljava/util/function/Function;Lnet/minecraft/client/resources/model/ModelState;)Lnet/minecraft/client/resources/model/BakedModel;", at = @At("RETURN"))
    BakedModel ap$customBake(BakedModel original, ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState) {
        if (architects_palette$customModelData != null) {
            var loader = CustomModelLoader.getLoader(architects_palette$customModelData.getLoader());
            if (loader != null) {
                return loader.bake(modelBaker, spriteGetter, modelState, original, (BlockModel) (Object) this);
            }
        }
        return original;
    }
}
