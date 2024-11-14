package architectspalette.core.api;

import com.google.common.base.Preconditions;
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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static architectspalette.core.APConstants.LOGGER;

public interface CustomModelLoader {
    Map<ResourceLocation, CustomModelLoader> LOADERS = new HashMap<>();

    static CustomModelLoader getLoader(String sid) {
        var id = ResourceLocation.read(sid);
        if (id.isError()) {
            LOGGER.error("Failed to get loader for {}", sid);
            return null;
        }

        return LOADERS.get(id.getOrThrow());
    }

    static CustomModelLoader getLoader(ResourceLocation id) {
        return LOADERS.get(id);
    }

    static void register(ResourceLocation name, CustomModelLoader loader) {
        Preconditions.checkArgument(!LOADERS.containsKey(name), "Geometry loader already registered: " + name);
        LOADERS.put(name, loader);
    }

    BlockModel read(JsonObject obj, Type type, JsonDeserializationContext deserializationContext);

    BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, BakedModel  bakedModel, BlockModel blockModel);

    interface APCustomModelData {
        ResourceLocation getLoader();
    }
}
