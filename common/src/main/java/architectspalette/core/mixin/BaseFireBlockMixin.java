package architectspalette.core.mixin;

import architectspalette.content.blocks.GreenFireBlock;
import architectspalette.core.registry.APBlocks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

   //Idk if this is the best way to implement a new fire, it was just the most straight forward. I did no research.
    @ModifyReturnValue(method = "getState", at = @At("RETURN"))
    private static BlockState architectsPaletteAddGreenFireMixin2(BlockState original, BlockGetter getter, BlockPos pos) {
        if (GreenFireBlock.canHeGreen(getter, pos.below())) {
            return APBlocks.NETHER_BRASS_FIRE.get().defaultBlockState();
        }
        return original;
    }
}
