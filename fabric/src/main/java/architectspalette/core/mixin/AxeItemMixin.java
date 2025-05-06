package architectspalette.core.mixin;

import architectspalette.content.blocks.StrippableBlock;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
    @ModifyReturnValue(method = "getStripped", at = @At("RETURN"))
    private Optional<BlockState> addCustomStrippingBlocks(Optional<BlockState> original, BlockState unstriped) {
        if (original.isEmpty() && unstriped.getBlock() instanceof StrippableBlock strippable) {
            if (strippable.shouldCopyProperties()) {
                return Optional.of(strippable.getStripedBlockState(unstriped));
            }
            return Optional.of(strippable.getStripToBlock().defaultBlockState());
        }
        return original;
    }
}
