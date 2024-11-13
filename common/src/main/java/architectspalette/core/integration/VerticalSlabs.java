package architectspalette.core.integration;

import architectspalette.content.blocks.VerticalSlabBlock;
import architectspalette.core.APConstants;
import architectspalette.core.config.APConfig;
import architectspalette.core.platform.Services;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public abstract class VerticalSlabs {
    public static boolean quarkEnabled, checkedQuark;
    public static String QUARK_ID = "quark";
    public static ResourceLocation VERTICAL_SLABS_CONDITION = APConstants.modLoc("enable_vertical_slabs");

    public static boolean areVisible() {
        if (!checkedQuark) {
            quarkEnabled = Services.PLATFORM.isModLoaded(QUARK_ID);
            checkedQuark = true;
        }
        return APConfig.VERTICAL_SLABS_FORCED.get() || quarkEnabled;
    }

    public static JsonObject conditionObj() {
        JsonObject dummyObject = new JsonObject();
        dummyObject.addProperty("type", "quark:flag");
        dummyObject.addProperty("flag", "vertical_slabs");
        return dummyObject;
    }

    public static boolean isVisible(Supplier<? extends Item> item) {
        if (item.get() instanceof BlockItem block && block.getBlock() instanceof VerticalSlabBlock) {
            return areVisible();
        } else return true;
    }
}
