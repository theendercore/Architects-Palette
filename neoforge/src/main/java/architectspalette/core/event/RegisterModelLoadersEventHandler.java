package architectspalette.core.event;

import architectspalette.core.model.BoardModel;
import architectspalette.core.model.util.SpriteShift;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static architectspalette.core.APConstants.*;

public class RegisterModelLoadersEventHandler {

    static {
        WrappedModelLoader.register(MODEL_TYPE_BOARDS, (context, bakedModel, blockModel) -> {
            ResourceLocation texture = blockModel.getMaterial("particle").texture();
            return new BoardModel(bakedModel, SpriteShift.getShift(texture, texture.withSuffix("_odd")));
        });
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(WRAPPER_LOADER, new WrappedModelLoader());
    }

    @FunctionalInterface
    private interface ModelWrapper {
        BakedModelWrapper<?> apply(IGeometryBakingContext context, BakedModel bakedModel, BlockModel blockModel);
    }

    private static class WrappedModelLoader implements IGeometryLoader<WrappedUnbakedModel> {
        //        private static final ElementsModel.Loader LOADER = ElementsModel.Loader.INSTANCE;
        private static final Map<String, ModelWrapper> wrappers = new HashMap<>();

        @SuppressWarnings("SameParameterValue")
        private static void register(String name, ModelWrapper wrappingFunction) {
            wrappers.put(name, wrappingFunction);
        }

        @Override
        public WrappedUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
            var wrappedModel = jsonObject.get("wrapped_model").getAsJsonObject();
            var wrapperType = jsonObject.get("wrapper_type").getAsString();

            BlockModel baseModel = deserializationContext.deserialize(wrappedModel, BlockModel.class);
            // Wrap the model, giving the wrapping function assigned to the wrapper type.
            return new WrappedUnbakedModel(baseModel, wrappers.get(wrapperType));
        }
    }

    private static class WrappedUnbakedModel implements IUnbakedGeometry<WrappedUnbakedModel> {
        private final BlockModel wrappedModel;
        private final ModelWrapper wrapper;

        public WrappedUnbakedModel(BlockModel modelToWrap, ModelWrapper func) {
            this.wrappedModel = modelToWrap;
            this.wrapper = func;
        }

        @Override
        public BakedModel bake(IGeometryBakingContext context, ModelBaker bakery, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
            BakedModel baked = wrappedModel.bake(bakery, wrappedModel, spriteGetter, modelState, context.useBlockLight());
            return wrapper.apply(context, baked, wrappedModel);
        }

        @Override
        public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
            wrappedModel.resolveParents(modelGetter);
        }
    }
}
