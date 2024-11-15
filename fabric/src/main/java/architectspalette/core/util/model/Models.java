package architectspalette.core.util.model;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

import java.util.Optional;

import static architectspalette.core.APConstants.rl;

public interface Models {
    TextureSlot FACE = key("face");
    TextureSlot INNER = key("inner");
    TextureSlot BARS = key("bars");

    TextureSlot HALF = key("half");
    TextureSlot RIGHT = key("right");
    TextureSlot LEFT = key("left");

    ModelTemplate TILES = template("tiles", TextureSlot.ALL);
    ModelTemplate FLAT_PANE = template("flat_pane", TextureSlot.TEXTURE);
    ModelTemplate CAGE_LANTERN = template("cage_lantern", TextureSlot.TEXTURE);
    ModelTemplate PIPE = template("pipe", TextureSlot.TEXTURE, FACE, INNER, TextureSlot.EDGE);

    ModelTemplate NUB = template("nub", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
    ModelTemplate NUB_HORIZONTAL = template("nub_horizontal", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);

    ModelTemplate VERTICAL_SLAB = template("vertical_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
    ModelTemplate Vertical_SLAB_TINTED = template("vertical_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);

    // Bars
    ModelTemplate BARS_POST_ENDS = template("bars/post_ends","_post_ends", TextureSlot.EDGE);
    ModelTemplate BARS_POST = template("bars/post", "_post", BARS);
    ModelTemplate BARS_CAP = template("bars/cap", "_cap", BARS, TextureSlot.EDGE);
    ModelTemplate BARS_CAP_ALT = template("bars/cap_alt","_cap_alt", BARS, TextureSlot.EDGE);
    ModelTemplate BARS_SIDE = template("bars/side","_side", BARS, TextureSlot.EDGE, TextureSlot.TOP);
    ModelTemplate BARS_SIDE_ALT = template("bars/side_alt", "_side_alt", BARS, TextureSlot.EDGE, TextureSlot.TOP);

    // Railings
    ModelTemplate RAILING_POST = template("railing/post", "_post", TextureSlot.TEXTURE);
    ModelTemplate RAILING_SIDE = template("railing/side", "_side", TextureSlot.TEXTURE);
    ModelTemplate RAILING_INVENTORY = template("railing/inventory", "_inventory", TextureSlot.TEXTURE);

    ModelTemplate CUSTOM_RAILING_POST = template("railing/custom_post", "_post", TextureSlot.TEXTURE);
    ModelTemplate CUSTOM_RAILING_SIDE_NORTH = template("railing/custom_side_north", "_side_north", TextureSlot.TEXTURE);
    ModelTemplate CUSTOM_RAILING_SIDE_EAST = template("railing/custom_side_east", "_side_east", TextureSlot.TEXTURE);
    ModelTemplate CUSTOM_RAILING_SIDE_SOUTH = template("railing/custom_side_south", "_side_south", TextureSlot.TEXTURE);
    ModelTemplate CUSTOM_RAILING_SIDE_WEST = template("railing/custom_side_west", "_side_west", TextureSlot.TEXTURE);
    ModelTemplate CUSTOM_RAILING_INVENTORY = template("railing/custom_inventory", "_inventory", TextureSlot.TEXTURE);

    // HEAVY Bricks
    ModelTemplate HEAVY_BRICKS_HALF = template("heavy_bricks/half", "_half", HALF);

    ModelTemplate HEAVY_BRICKS_BOTTOM = template("heavy_bricks/bottom", "_bottom", TextureSlot.BOTTOM, HALF);
    ModelTemplate HEAVY_BRICKS_TOP = template("heavy_bricks/top", "_top", TextureSlot.TOP, HALF);

    ModelTemplate HEAVY_BRICKS_NORTH = template("heavy_bricks/north", "_north", TextureSlot.TOP, TextureSlot.BOTTOM, RIGHT, LEFT, HALF);
    ModelTemplate HEAVY_BRICKS_EAST = template("heavy_bricks/east", "_east", RIGHT, LEFT, HALF);
    ModelTemplate HEAVY_BRICKS_SOUTH = template("heavy_bricks/south", "_south", TextureSlot.TOP, TextureSlot.BOTTOM, RIGHT, LEFT, HALF);
    ModelTemplate HEAVY_BRICKS_WEST = template("heavy_bricks/west", "_west", RIGHT, LEFT, HALF);

    // Fancy Walls
    ModelTemplate FW_CENTER_SHORT = fancyWall("center_short", "_center_short", TextureSlot.WALL);
    ModelTemplate FW_CENTER_TALL = fancyWall("center_tall", "_center_tall", TextureSlot.WALL);

    ModelTemplate FW_NORTH_SHORT = fancyWall("north_short", "_north_short", TextureSlot.WALL);
    ModelTemplate FW_NORTH_TALL = fancyWall("north_tall", "_north_tall", TextureSlot.WALL);

    ModelTemplate FW_EAST_SHORT = fancyWall("east_short", "_east_short", TextureSlot.WALL);
    ModelTemplate FW_EAST_TALL = fancyWall("east_tall", "_east_tall", TextureSlot.WALL);

    ModelTemplate FW_SOUTH_SHORT = fancyWall("south_short", "_south_short", TextureSlot.WALL);
    ModelTemplate FW_SOUTH_TALL = fancyWall("south_tall", "_south_tall", TextureSlot.WALL);

    ModelTemplate FW_WEST_SHORT = fancyWall("west_short", "_west_short", TextureSlot.WALL);
    ModelTemplate FW_WEST_TALL = fancyWall("west_tall", "_west_tall", TextureSlot.WALL);

    ModelTemplate FW_POST = fancyWall("post", "_post", TextureSlot.WALL);
    ModelTemplate FW_INVENTORY = fancyWall("inventory", "_inventory", TextureSlot.WALL);

    private static ModelTemplate fancyWall(String string, String suffix, TextureSlot... textureSlots) {
        return createModel("template/fancy_walls/" + string, Optional.of(suffix), textureSlots);
    }

    private static ModelTemplate template(String string, TextureSlot... textureSlots) {
        return createModel("template/" + string, Optional.empty(), textureSlots);
    }

    private static ModelTemplate template(String string, String suffix, TextureSlot... textureSlots) {
        return createModel("template/" + string, Optional.of(suffix), textureSlots);
    }

    static ModelTemplate createModel(String string, Optional<String> suffix, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(rl("block/" + string)), suffix, textureSlots);
    }

    private static TextureSlot key(String string) {
        return TextureSlot.create(string);
    }
}
