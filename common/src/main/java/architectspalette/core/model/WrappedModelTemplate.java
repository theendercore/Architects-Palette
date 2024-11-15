package architectspalette.core.model;

import com.google.gson.JsonObject;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    // (ender) some reflection cuz im too lazy to write an accessor
    @SuppressWarnings({"unchecked", "SameParameterValue"})
    public  static WrappedModelTemplate wrap(ModelTemplate template, String type) {
        try {
            Field modelFiled = template.getClass().getDeclaredField("model");
            modelFiled.setAccessible(true);
            Field requiredSlotsFiled = template.getClass().getDeclaredField("requiredSlots");
            requiredSlotsFiled.setAccessible(true);
            Field suffixFiled = template.getClass().getDeclaredField("suffix");
            suffixFiled.setAccessible(true);

            return new WrappedModelTemplate(
                    (Optional<ResourceLocation>) modelFiled.get(template),
                    (Optional<String>) suffixFiled.get(template),
                    type,
                    ((Set<TextureSlot>) requiredSlotsFiled.get(template)).toArray(new TextureSlot[0])
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
