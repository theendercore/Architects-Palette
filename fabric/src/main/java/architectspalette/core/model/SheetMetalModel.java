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

public class SheetMetalModel extends ForwardingBakedModel implements SheetMetalHelper {
    private final SpriteShift spriteShift;

    public SheetMetalModel(BakedModel parent, SpriteShift spriteShift) {
        wrapped = parent;
        this.spriteShift = spriteShift;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override // (Ender) Stolen from AP Fabric
    public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        var data = new Data();
        SheetMetalHelper.initializeData(blockView, pos, state, data);
        context.pushTransform(quad -> {
            var dir = quad.lightFace();
            var index = data.get(dir);
            if (index != -1) {
                float uShift = spriteShift.getUShift();
                float vShift = spriteShift.getVShift() + (spriteShift.getVHeight() * index);

                for (int vertex = 0; vertex < 4; vertex++) {
                    float u = quad.u(vertex);
                    float v = quad.v(vertex);
                    quad.uv(vertex, u + uShift, v + vShift);
                }

            }
            return true;
        });
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        context.popTransform();
    }
}