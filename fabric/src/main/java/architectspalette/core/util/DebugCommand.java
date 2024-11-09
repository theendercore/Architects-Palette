package architectspalette.core.util;

import architectspalette.core.platform.Services;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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
            world.setBlock(pos.below(), Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState(), Block.UPDATE_ALL);
            world.setBlock(pos, block.defaultBlockState(), Block.UPDATE_ALL);

            if (row > 24) {
                height++;
                row = 0;
                continue;
            }
            row++;
        }
        return 0;
    }
}
