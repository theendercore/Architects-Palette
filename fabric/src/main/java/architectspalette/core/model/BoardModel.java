package architectspalette.core.model;

import architectspalette.core.model.util.SpriteShift;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BoardModel extends ForwardingBakedModel implements BoardHelper {
    private final SpriteShift spriteShift;

    public BoardModel(BakedModel parent, SpriteShift spriteShift) {
        wrapped = parent;
        this.spriteShift = spriteShift;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        var data = new BoardData(pos);
        context.pushTransform(quad -> {
            if (BoardHelper.shouldShift(quad.lightFace(), data)) {
                for (int vertex = 0; vertex < 4; vertex++) {
                    float u = quad.u(vertex);
                    float v = quad.v(vertex);
                    quad.uv(vertex, u + spriteShift.getUShift(), v + spriteShift.getVShift());
                }
            }
            return true;
        });
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        context.popTransform();
    }
}