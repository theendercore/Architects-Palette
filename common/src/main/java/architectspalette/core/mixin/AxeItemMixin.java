package architectspalette.core.mixin;

import architectspalette.content.blocks.StrippableBlock;
import architectspalette.content.blocks.util.APWeatheringCopper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
    @Unique
    private boolean isAPWeatheringCopper = false;

 /*   @Inject(method = "evaluateNewBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/WeatheringCopper;getPrevious(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;"), cancellable = true)
    private void doTheCopperMagicEvents(Level level, BlockPos pos, Player player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (isAPWeatheringCopper) {
            Optional<BlockState> waxed = APWeatheringCopper.getUnWaxed(state);
            if (waxed.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3004, pos, 0);
                cir.setReturnValue(waxed);
                return;
            }
            Optional<BlockState> weathered = APWeatheringCopper.getPrevious(state);
            if (weathered.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3005, pos, 0);
                cir.setReturnValue(weathered);
            }
        }
    }*/

    @Inject(method = "getStripped", at = @At("RETURN"), cancellable = true)
    private void doTheMagicStripping(BlockState oldState, CallbackInfoReturnable<Optional<BlockState>> cir) {
        if (cir.getReturnValue().isEmpty()) {
            if (oldState.getBlock() instanceof StrippableBlock strippable) {
                if (strippable instanceof APWeatheringCopper) {
                    isAPWeatheringCopper = true;
                    return;
                }
                if (strippable.getStripToBlock() == null) return;
                assert strippable.getStripToBlock() != null;
                BlockState newState = (strippable.shouldCopyProperties())
                        ? strippable.getStripedBlockState(oldState.getValues())
                        : strippable.getStripToBlock().defaultBlockState();
                cir.setReturnValue(Optional.of(newState));
            }
        }
    }
}
