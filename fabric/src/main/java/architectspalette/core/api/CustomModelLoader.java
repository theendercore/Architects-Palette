package architectspalette.core.api;

import architectspalette.core.api.impl.CustomLoaderManager;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.function.Function;

public interface CustomModelLoader {

    static CustomModelLoader getLoader(String sid) {
        return CustomLoaderManager.IMPL.getLoader(sid);
    }

    static CustomModelLoader getLoader(ResourceLocation id) {
        return CustomLoaderManager.IMPL.getLoader(id);
    }

    static void register(ResourceLocation name, CustomModelLoader loader) {
        CustomLoaderManager.IMPL.register(name, loader);
    }

    BlockModel read(JsonObject obj, Type type, JsonDeserializationContext deserializationContext);

    BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, BakedModel bakedModel, BlockModel blockModel);

    interface APCustomModelData {
        ResourceLocation getLoader();
    }
}
