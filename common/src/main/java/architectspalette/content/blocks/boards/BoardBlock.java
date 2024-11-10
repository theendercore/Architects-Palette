package architectspalette.content.blocks.boards;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;


public class BoardBlock extends Block implements Boards {
    public BoardBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, Boards.BoardType.NORMAL));
    }

    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(TYPE, getType(context.getClickedPos()));
    }

    @Override
    protected @NotNull BlockState updateShape(BlockState oldState, Direction dir, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return setState(super.updateShape(oldState, dir, neighborState, world, pos, neighborPos), pos);
    }
}
