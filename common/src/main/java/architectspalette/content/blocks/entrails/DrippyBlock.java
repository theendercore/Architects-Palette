package architectspalette.content.blocks.entrails;

import architectspalette.core.registry.util.IBlockSetBase;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;


public class DrippyBlock extends Block implements IBlockSetBase {
    public DrippyBlock(Properties properties) {
        super(properties);
    }

    //IBlockSetBase method
    public Block getBlockForPart(StoneBlockSet.SetComponent part, Properties properties, Block base) {
        return switch (part) {
            case SLAB -> new DrippySlabBlock(properties);
            case VERTICAL_SLAB -> new DrippyVerticalSlabBlock(properties);
            default -> IBlockSetBase.super.getBlockForPart(part, properties, base);
        };
    }


//    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        doParticleEffect(stateIn, worldIn, pos, rand);
    }

//    @OnlyIn(Dist.CLIENT)
    public static void doParticleEffect(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (rand.nextInt(6) == 0) {
            Direction direction = Direction.getRandom(rand);
            if (direction != Direction.UP && direction != Direction.DOWN) {
                BlockPos posBelow = pos.relative(Direction.DOWN);
                BlockState stateBelow = worldIn.getBlockState(posBelow);
                if (!stateIn.canOcclude() || !stateBelow.isFaceSturdy(worldIn, posBelow, Direction.UP)) {
                    double xOffset = direction.getStepX() == 0 ? rand.nextDouble() : 0.5D + (double)direction.getStepX() * 0.45D;
                    double zOffset = direction.getStepZ() == 0 ? rand.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.45D;
                    Vec3 dropPos = new Vec3(pos.getX() + xOffset, pos.getY() - .1, pos.getZ() + zOffset);
                    BlockHitResult rayTraceResult = stateIn.getOcclusionShape(worldIn, pos).clip(dropPos, dropPos.add(0, 1, 0), pos);
                    if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS) {
                        Vec3 result = rayTraceResult.getLocation();
                        worldIn.addParticle(ParticleTypes.DRIPPING_WATER, result.x(), result.y() - .1, result.z(), 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }
}
