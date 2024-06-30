package architectspalette.content.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;

public interface StrippableBlock {
    //(ender) that that java, "oh that's not safe 🤓". yeah, right I know what im doing
    @SuppressWarnings("unchecked")
    default <T extends Comparable<T>> BlockState getStripedBlockState(Map<Property<?>, Comparable<?>> oldState) {
        if (oldState == null) return getStripToBlock().defaultBlockState();
        else {
            BlockState block = getStripToBlock().defaultBlockState();
            for (Map.Entry<Property<?>, Comparable<?>> entry : oldState.entrySet()) {
                if (block.hasProperty(entry.getKey()))
                    block = block.setValue((Property<T>) entry.getKey(), (T) entry.getValue());
            }
            return block;
        }
    }

    Block getStripToBlock();

    default boolean shouldCopyProperties() {
        return false;
    }
}
