package architectspalette.core.mixin;

import architectspalette.core.api.CustomModelLoader;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.model.BlockModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(BlockModel.Deserializer.class)
public class BlockModelDeserializerMixin {
    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/renderer/block/model/BlockModel;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockModel$Deserializer;getElements(Lcom/google/gson/JsonDeserializationContext;Lcom/google/gson/JsonObject;)Ljava/util/List;", ordinal = 0), cancellable = true)
    void ap$customModelWrapper(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<BlockModel> cir,
                               @Local JsonObject obj) {
        if (obj.has("loader")) {
            var loader = CustomModelLoader.getLoader(obj.get("loader").getAsString());
            if (loader != null) {
                cir.setReturnValue(loader.read(obj, type, jsonDeserializationContext));
            }
        }
    }
}
