package architectspalette.content.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public interface StrippableBlock {
    default BlockState getStripedBlockState(BlockState state) {
        return getStripToBlock().withPropertiesOf(state);
    }

    @Nonnull
    Block getStripToBlock();

    default boolean shouldCopyProperties() {
        return false;
    }
}
