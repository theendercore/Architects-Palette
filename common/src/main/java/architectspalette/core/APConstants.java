package architectspalette.core;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APConstants {

    public static final String MOD_ID = "architects_palette";
    public static final String MOD_NAME = "ArchitectsPalette";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final String MODEL_TYPE_BOARDS = "boards";
    public static final ResourceLocation WRAPPER_LOADER = rl("wrapped");

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

}