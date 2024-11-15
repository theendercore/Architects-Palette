package architectspalette.core.api.impl;

import architectspalette.core.api.CustomModelLoader;
import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

import static architectspalette.core.APConstants.LOGGER;

@ApiStatus.Internal
public class CustomLoaderManager {
    public static CustomLoaderManager IMPL = new CustomLoaderManager();
    private final Map<ResourceLocation, CustomModelLoader> LOADERS = new HashMap<>();

    public CustomModelLoader getLoader(String sid) {
        var id = ResourceLocation.read(sid);
        if (id.isError()) {
            LOGGER.error("Failed to parse ResourceLocation for \"{}\"", sid);
            return null;
        }

        return LOADERS.get(id.getOrThrow());
    }

    public CustomModelLoader getLoader(ResourceLocation id) {
        return LOADERS.get(id);
    }

    public void register(ResourceLocation name, CustomModelLoader loader) {
        Preconditions.checkArgument(!LOADERS.containsKey(name), "Custom loader already registered: " + name);
        LOADERS.put(name, loader);
    }
}
