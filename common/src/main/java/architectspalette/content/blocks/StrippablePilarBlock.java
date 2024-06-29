package architectspalette.content.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class StrippablePilarBlock extends RotatedPillarBlock implements StrippableBlock {

    private final Block stripsTo;

    public StrippablePilarBlock(Properties properties, Block stripsTo) {
        super(properties);
        this.stripsTo = stripsTo;
    }

    @Override
    public Block getStripToBlock() {
        return stripsTo;
    }

    @Override
    public boolean shouldCopyProperties() {
        return true;
    }
}
