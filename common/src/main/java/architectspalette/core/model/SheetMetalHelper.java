package architectspalette.core.model;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public interface SheetMetalHelper {
    static void initializeData(BlockAndTintGetter world, BlockPos pos, BlockState state, Data data) {
        var checkPos = new BlockPos.MutableBlockPos();
        for (var face : Direction.values()) {
            if (Block.shouldRenderFace(state, world, pos, face, checkPos.setWithOffset(pos, face)) || state.getBlock() instanceof WallBlock) {
                int index = 1;
                boolean doShift = false;
                if (world.getBlockState(checkPos.setWithOffset(pos, getUpDirection(face))).is(state.getBlock())) {
                    index += 1;
                    doShift = true;
                }
                if (world.getBlockState(checkPos.setWithOffset(pos, getDownDirection(face))).is(state.getBlock())) {
                    index -= 1;
                    doShift = true;
                }
                if (doShift) {
                    data.set(face, index);
                }
            }
        }
    }

    static Direction getUpDirection(Direction face) {
        return switch (face) {
            case UP -> Direction.NORTH;
            case DOWN -> Direction.SOUTH;
            default -> Direction.UP;
        };
    }

    static Direction getDownDirection(Direction face) {
        return getUpDirection(face).getOpposite();
    }

    class Data {
        private final int[] indices = new int[6];

        public Data() {
            Arrays.fill(indices, -1);
        }

        public void set(Direction face, int index) {
            indices[face.get3DDataValue()] = index;
        }

        public int get(Direction face) {
            return indices[face.get3DDataValue()];
        }

    }
}
