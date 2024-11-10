package architectspalette.content.blocks.boards;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;

public interface Boards {
    EnumProperty<BoardType> TYPE = EnumProperty.create("type", BoardType.class);

    default BlockState setState(BlockState state, BlockPos pos) {
        return (state.hasProperty(TYPE)) ? state.setValue(TYPE, getType(pos)) : state;
    }


    default Boards.BoardType getType(BlockPos pos) {
        var areEndsOdd = pos.getX() % 2 == 0;
        var areSidesOdd = pos.getX() % 2 == 0 ^ pos.getZ() % 2 == 0;
        if (areEndsOdd && areSidesOdd) return Boards.BoardType.ODD;
        else if (!areSidesOdd && areEndsOdd) return Boards.BoardType.ENDS_ODD;
        else if (areSidesOdd) return Boards.BoardType.SIDES_ODD;
        return Boards.BoardType.NORMAL;
    }

    enum BoardType implements StringRepresentable {
        NORMAL, ODD, ENDS_ODD, SIDES_ODD;

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
