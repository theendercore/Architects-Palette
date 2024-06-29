package architectspalette.core.mixin;

import architectspalette.content.blocks.StrippableBlock;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    @Inject(method = "getStripped", at = @At("RETURN"), cancellable = true)
    private void  doTheMagicStripping(BlockState oldState, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (cir.getReturnValue().isEmpty()) {
            if (oldState.getBlock() instanceof StrippableBlock strippable) {
                BlockState newState = (strippable.shouldCopyProperties())
                        ? strippable.getStripedBlockState(oldState.getValues())
                        : strippable.getStripToBlock().defaultBlockState();
                cir.setReturnValue(Optional.of(newState));
            }
        }
    }
}
