package architectspalette.core.model.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.block.model.BakedQuad;

import java.util.Arrays;

// Copied from Create's QuadHelper (As per the license, Copyright (c) simibubi)
// https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/block/render/QuadHelper.java
// (ender) update link https://github.com/Creators-of-Create/Create/blob/mc1.20.1/dev/src/main/java/com/simibubi/create/foundation/model/BakedQuadHelper.java#L11
// I'm not really sure how this math works, so I can't really appropriate it in any meaningful way.
public class QuadHelper {

    public static final VertexFormat FORMAT = DefaultVertexFormat.BLOCK;
    public static final int VERTEX_STRIDE = FORMAT.getVertexSize() / 4;

//    public static final int X_OFFSET = 0;
//    public static final int Y_OFFSET = 1;
//    public static final int Z_OFFSET = 2;
//    public static final int COLOR_OFFSET = 3;
    public static final int U_OFFSET = 4;
    public static final int V_OFFSET = 5;
//    public static final int LIGHT_OFFSET = 6;
//    public static final int NORMAL_OFFSET = 7;

    private QuadHelper() {
    }

    public static BakedQuad clone(BakedQuad quad) {
        return new BakedQuad(Arrays.copyOf(quad.getVertices(), quad.getVertices().length),
                quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
    }

    public static float getU(int[] vertexData, int vertex) {
        return Float.intBitsToFloat(vertexData[vertex * VERTEX_STRIDE + U_OFFSET]);
    }

    public static float getV(int[] vertexData, int vertex) {
        return Float.intBitsToFloat(vertexData[vertex * VERTEX_STRIDE + V_OFFSET]);
    }

    public static void setU(int[] vertexData, int vertex, float u) {
        vertexData[vertex * VERTEX_STRIDE + U_OFFSET] = Float.floatToRawIntBits(u);
    }

    public static void setV(int[] vertexData, int vertex, float v) {
        vertexData[vertex * VERTEX_STRIDE + V_OFFSET] = Float.floatToRawIntBits(v);
    }
}
