package architectspalette.core.model;

import architectspalette.core.mixin.ModelTemplateAccessor;
import com.google.gson.JsonObject;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

import static architectspalette.core.APConstants.MODEL_TYPE_BOARDS;

public class WrappedModelTemplate extends ModelTemplate {
    public String type;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public WrappedModelTemplate(Optional<ResourceLocation> optional, Optional<String> optional2, String type, TextureSlot... textureSlots) {
        super(optional, optional2, textureSlots);
        this.type = type;
    }


    public static WrappedModelTemplate boardWarp(ModelTemplate template) {
        return wrap(template, MODEL_TYPE_BOARDS);
    }

    public static WrappedModelTemplate wrap(ModelTemplate template, String type) {
        var openTemplate = (ModelTemplateAccessor) template;
        return new WrappedModelTemplate(openTemplate.getModel(), openTemplate.getSuffix(), type, openTemplate.getRequiredSlots().toArray(new TextureSlot[0]));
    }

    @Override
    public @NotNull JsonObject createBaseTemplate(@NotNull ResourceLocation resourceLocation, @NotNull Map<TextureSlot, ResourceLocation> map) {
        var wrappedModel = new JsonObject();
        wrappedModel.addProperty("loader", "architects_palette:wrapped");
        wrappedModel.addProperty("wrapper_type", type);
        wrappedModel.add("wrapped_model", super.createBaseTemplate(resourceLocation, map));
        return wrappedModel;
    }
}
