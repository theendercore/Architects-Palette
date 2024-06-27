package architectspalette.core;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APConstants {

    public static final String MOD_ID = "architects_palette";
    public static final String MOD_NAME = "ArchitectsPalette";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    public static final ResourceLocation VERTICAL_SLABS_CONDITION = modLoc("enable_vertical_slabs");
}