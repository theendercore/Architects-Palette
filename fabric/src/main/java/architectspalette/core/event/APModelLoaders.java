package architectspalette.core.event;

import architectspalette.core.api.CustomModelLoader;
import architectspalette.core.model.BoardModel;
import architectspalette.core.model.util.SpriteShift;
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

import static architectspalette.core.APConstants.modLoc;
import static architectspalette.core.util.APCustomModelDataAccessor.getData;
import static architectspalette.core.util.APCustomModelDataAccessor.setData;

public class APModelLoaders {
    public static final String MODEL_TYPE_BOARDS = "boards";
    public static final ResourceLocation WRAPPER_LOADER = modLoc("wrapped");

    static {
        WrappedModelLoader.register(MODEL_TYPE_BOARDS, (baked, model) -> {
            ResourceLocation texture = model.getMaterial("particle").texture();
            return new BoardModel(baked, SpriteShift.getShift(texture, texture.withSuffix("_odd")));
        });
    }

    public static void init() {
        CustomModelLoader.register(WRAPPER_LOADER, new WrappedModelLoader());
    }


    @FunctionalInterface
    public interface ModelWrapper {
        BakedModel apply(BakedModel bakedModel, BlockModel blockModel);
    }

    static class WrapperModelData implements CustomModelLoader.APCustomModelData {
        String wrapper;

        public WrapperModelData(String wrapper) {
            this.wrapper = wrapper;
        }

        @Override
        public ResourceLocation getLoader() {
            return WRAPPER_LOADER;
        }
    }

    public static class WrappedModelLoader implements CustomModelLoader {
        private static final Map<String, ModelWrapper> WRAPPERS = new HashMap<>();

        public static void register(String name, ModelWrapper wrappingFunction) {
            WRAPPERS.put(name, wrappingFunction);
        }


        @Override
        public BlockModel read(JsonObject obj, Type type, JsonDeserializationContext deserializationContext) {
            var wrappedModel = obj.get("wrapped_model").getAsJsonObject();
            var wrapperType = obj.get("wrapper_type").getAsString();

            BlockModel baseModel = deserializationContext.deserialize(wrappedModel, BlockModel.class);
            if (wrapperType == null) return baseModel;

            // set the custom data with a little hidden mixin
            return setData(baseModel, new WrapperModelData(wrapperType));
        }

        @Override
        public BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, BakedModel bakedModel, BlockModel blockModel) {
            var modelData = getData(blockModel);
            if (modelData instanceof WrapperModelData data)
                return WRAPPERS.get(data.wrapper).apply(bakedModel, blockModel);
            return bakedModel;
        }
    }
}
