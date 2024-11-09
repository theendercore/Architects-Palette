package architectspalette.core.util.model;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

import java.util.Optional;

import static architectspalette.core.APConstants.modLoc;

public interface Models {
    TextureSlot FACE = key("face");
    TextureSlot INNER = key("inner");

    TextureSlot HALF = key("half");
    TextureSlot RIGHT = key("right");
    TextureSlot LEFT = key("left");

    ModelTemplate NUB = template("nub", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
    ModelTemplate NUB_HORIZONTAL = template("nub_horizontal", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);

    ModelTemplate VERTICAL_SLAB = template("vertical_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);
    ModelTemplate Vertical_SLAB_TINTED = template("vertical_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);

    ModelTemplate RAILING_POST = template("railing/post", "_post", TextureSlot.TEXTURE);
    ModelTemplate RAILING_SIDE = template("railing/side", "_side", TextureSlot.TEXTURE);
    ModelTemplate RAILING_INVENTORY = template("railing/inventory", "_inventory", TextureSlot.TEXTURE);

    ModelTemplate CAGE_LANTERN = template("cage_lantern", TextureSlot.TEXTURE);
    ModelTemplate PIPE = template("pipe", TextureSlot.TEXTURE, FACE, INNER, TextureSlot.EDGE);

    // HEAVY Bricks
    ModelTemplate HEAVY_BRICKS_HALF = template("heavy_bricks/half", "_half", HALF);

    ModelTemplate HEAVY_BRICKS_BOTTOM = template("heavy_bricks/bottom","_bottom", TextureSlot.BOTTOM, HALF);
    ModelTemplate HEAVY_BRICKS_TOP = template("heavy_bricks/top","_top", TextureSlot.TOP, HALF);

    ModelTemplate HEAVY_BRICKS_NORTH = template("heavy_bricks/north","_north", TextureSlot.TOP, TextureSlot.BOTTOM, RIGHT, LEFT, HALF);
    ModelTemplate HEAVY_BRICKS_EAST = template("heavy_bricks/east", "_east", RIGHT, LEFT, HALF);
    ModelTemplate HEAVY_BRICKS_SOUTH = template("heavy_bricks/south","_south", TextureSlot.TOP, TextureSlot.BOTTOM, RIGHT, LEFT, HALF);
    ModelTemplate HEAVY_BRICKS_WEST = template("heavy_bricks/west","_west", RIGHT, LEFT, HALF);


    private static ModelTemplate template(String string, TextureSlot... textureSlots) {
        return model("template/" + string, Optional.empty(), textureSlots);
    }

    private static ModelTemplate template(String string, String suffix, TextureSlot... textureSlots) {
        return model("template/" + string, Optional.of(suffix), textureSlots);
    }

    private static ModelTemplate model(String string, Optional<String> suffix, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(modLoc("block/" + string)), suffix, textureSlots);
    }

    private static TextureSlot key(String string) {
        return TextureSlot.create(string);
    }
}
