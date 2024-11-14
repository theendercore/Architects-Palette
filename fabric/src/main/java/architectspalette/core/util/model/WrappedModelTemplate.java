package architectspalette.core.util.model;

import com.google.gson.JsonObject;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class WrappedModelTemplate extends ModelTemplate {
    public WrappedModelTemplate(Optional<ResourceLocation> optional, Optional<String> optional2, TextureSlot... textureSlots) {
        super(optional, optional2, textureSlots);
    }

    // (ender) some reflection cuz im too lazy to write an accessor
    @SuppressWarnings("unchecked")
    static WrappedModelTemplate wrap(ModelTemplate template) {
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
        wrappedModel.addProperty("wrapper_type", "boards");
        wrappedModel.add("wrapped_model", super.createBaseTemplate(resourceLocation, map));
        return wrappedModel;
    }
}
