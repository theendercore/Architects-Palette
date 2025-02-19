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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BoardModel extends BakedModelWrapperWithData implements BoardHelper {
    //The model property used to store the board data.
    private static final ModelProperty<BoardData> BOARD_PROPERTY = new ModelProperty<>();
    private final SpriteShift spriteShift;

    public BoardModel(BakedModel originalModel, SpriteShift boardShift) {
        super(originalModel);
        this.spriteShift = boardShift;
    }

    //Add model property to the model data builder.
    @Override
    protected ModelData.Builder gatherModelData(ModelData.Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state) {
        return builder.with(BOARD_PROPERTY, new BoardData(pos));
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
        List<BakedQuad> quads = super.getQuads(state, side, rand, extraData, renderType);

        if (!extraData.has(BOARD_PROPERTY)) return quads;
        BoardData data = extraData.get(BOARD_PROPERTY);
        if (data == null) return quads;

        quads = new ArrayList<>(quads);

        for (int i = 0; i < quads.size(); i++) {
            BakedQuad quad = quads.get(i);
            if (BoardHelper.shouldShift(quad.getDirection(), data)) {
                BakedQuad newQuad = QuadHelper.clone(quad);
                int[] vertexData = newQuad.getVertices();
                float uShift = spriteShift.getUShift();
                float vShift = spriteShift.getVShift();

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
