package architectspalette.core.model;

import architectspalette.core.model.util.BakedModelWrapperWithData;
import architectspalette.core.model.util.QuadHelper;
import architectspalette.core.model.util.SpriteShift;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SheetMetalModel extends BakedModelWrapperWithData implements SheetMetalHelper {
    private static final ModelProperty<SheetData> CT_PROPERTY = new ModelProperty<>();
    private final SpriteShift spriteShift;

    public SheetMetalModel(BakedModel originalModel, SpriteShift spriteShift) {
        super(originalModel);
        this.spriteShift = spriteShift;
    }

    @Override
    protected ModelData.Builder gatherModelData(ModelData.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state) {
        var data = new SheetData();
        SheetMetalHelper.initializeData(world, pos, state, data);
        return builder.with(CT_PROPERTY, data);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        List<BakedQuad> quads = super.getQuads(state, side, rand, extraData, renderType);

        if (!extraData.has(CT_PROPERTY)) return quads;
        SheetData data = extraData.get(CT_PROPERTY);
        if (data == null) return quads;

        quads = new ArrayList<>(quads);

        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            var dir = quad.getDirection();
            var index = data.get(dir);
            if (index != -1) {
                BakedQuad newQuad = QuadHelper.clone(quad);
                int[] vertexData = newQuad.getVertices();
                float uShift = spriteShift.getUShift();
                float extraShift = (spriteShift.getVHeight() * index);
                float vShift = spriteShift.getVShift() + extraShift;

                for (int vertex = 0; vertex < 4; vertex++) {
                    float u = QuadHelper.getU(vertexData, vertex);
                    float v = QuadHelper.getV(vertexData, vertex);
                    QuadHelper.setU(vertexData, vertex, u + uShift);
                    QuadHelper.setV(vertexData, vertex, v + vShift);
                }

                quads.set(i, newQuad);
            }

        }

        return quads;
    }
}
