package architectspalette.content.blocks;

import architectspalette.core.registry.APTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GreenFireBlock extends BaseFireBlock {
    public static final MapCodec<GreenFireBlock> CODEC = simpleCodec(GreenFireBlock::new);
    //More or less a copy of SoulFireBlock. Just appropriated for its new purpose.
    public GreenFireBlock(Properties properties) {
        //Does three damage because I think it's funny
        super(properties, 3.0F);
    }
    @Override
    protected MapCodec<? extends GreenFireBlock> codec() {
        return CODEC;
    }

    //It's the little things. Pick block on the fire will give you flint and steel to light one. Vanilla doesn't do this.
    // (ender) so forge adds a custom getCloneItemStack method, why?
    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(Items.FLINT_AND_STEEL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction face, BlockState facingState, LevelAccessor levelAccessor, BlockPos thisPos, BlockPos facingPos) {
        return this.canSurvive(state, levelAccessor, thisPos) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean canSurvive(BlockState stateIn, LevelReader levelReader, BlockPos pos) {
        return canHeGreen(levelReader, pos.below());
    }

    //Soul Fire doesn't need to check for face being sturdy because the only things it works on are full blocks.
    //Green fire can go on slabs and stuff.
    public static boolean canHeGreen(BlockGetter getter, BlockPos pos) {
        BlockState state = getter.getBlockState(pos);
        return state.is(APTags.GREEN_FIRE_SUPPORTING) && state.isFaceSturdy(getter, pos, Direction.UP);
    }

    @Override
    protected boolean canBurn(BlockState p_49284_) {
        return true;
    }
}
