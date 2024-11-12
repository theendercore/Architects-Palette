package architectspalette.core.util;

import architectspalette.content.blocks.*;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APBlocks;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;

import java.util.Objects;

import static net.minecraft.commands.Commands.literal;

// (ender) I needed this for testing if I got all the models
public interface DebugCommand {
    static void registerDebugCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, bCtx, env) ->
                dispatcher.register(literal("ap_block").executes(DebugCommand::funnyPlace)));
    }

    static int funnyPlace(CommandContext<CommandSourceStack> ctx) {
        var world = ctx.getSource().getLevel();
        var origin = Objects.requireNonNull(ctx.getSource().getEntity()).blockPosition();

        var row = 0;
        var height = 0;
        for (Block block : Services.REGISTRY.getModBlocks()) {
            var pos = origin.east(row).north(height).above(height);
            var ground = Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState();
            if (block instanceof SaplingBlock) ground = Blocks.GRASS_BLOCK.defaultBlockState();
            else if (block instanceof GreenFireBlock) ground = APBlocks.NETHER_BRASS.get().defaultBlockState();
            world.setBlock(pos.below(), ground, Block.UPDATE_ALL);

            var state = block.defaultBlockState();
            if (block instanceof WallTorchBlock || block instanceof TotemWingBlock || block instanceof ButtonBlock || block instanceof TotemBlock) {
                state = state.setValue(WallTorchBlock.FACING, Direction.SOUTH);
            }
            switch (block) {
                case VerticalSlabBlock i ->
                        state = state.setValue(VerticalSlabBlock.TYPE, VerticalSlabBlock.VerticalSlabType.SOUTH);
                case NubBlock i -> state = state.setValue(NubBlock.FACING, Direction.DOWN);
                case CageLanternBlock i ->
                        state = state.setValue(CageLanternBlock.INVERTED, true).setValue(CageLanternBlock.LIT, true);
                default -> {
                }
            }

            world.setBlock(pos, state, Block.UPDATE_ALL);

            if (row > 27) {
                height++;
                row = 0;
                continue;
            }
            row++;
        }
        return 0;
    }
}
