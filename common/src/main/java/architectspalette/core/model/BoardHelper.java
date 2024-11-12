package architectspalette.core.model;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public interface BoardHelper {
    static boolean shouldShift(Direction dir, BoardData data) {
        var dirId = dir.get3DDataValue();
        boolean shift;
        //Check for being horizontal.
        if (dirId >= 2) {
            //If the horizontal faces should be shifted
            shift = data.getHorizontal();
            //Check for north/south
            if ((dirId < 4)) {
                shift = !shift;
            }
        }
        //Vertical face.
        else {
            //If the vertical faces should be shifted
            shift = data.getVertical();
            if (dirId == 0) {
                shift = !shift;
            }
        }
        return shift;
    }

    class BoardData {
        private final boolean isXOdd;
        private final boolean isZOdd;

        public BoardData(BlockPos pos) {
            this.isXOdd = isOdd(pos.getX());
            this.isZOdd = isOdd(pos.getZ());
        }

        private static boolean isOdd(int num) {
            return (num % 2) != 0;
        }

        public boolean getHorizontal() {
            return (isZOdd ^ isXOdd);
        }

        public boolean getVertical() {
            return isXOdd;
        }
    }
}
