package architectspalette.content.blocks.util;

import architectspalette.core.registry.APBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;
import java.util.function.Supplier;


//If you can't beat 'em, join 'em. (Vanilla Copper interface isn't extendable.)
public interface APWeatheringCopper extends ChangeOverTimeBlock<WeatheringCopper.WeatherState> {
    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() ->
    {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.put(APBlocks.COPPER_NUB.get(), APBlocks.EXPOSED_COPPER_NUB.get());
        builder.put(APBlocks.EXPOSED_COPPER_NUB.get(), APBlocks.WEATHERED_COPPER_NUB.get());
        builder.put(APBlocks.WEATHERED_COPPER_NUB.get(), APBlocks.OXIDIZED_COPPER_NUB.get());
        return builder.build();
    });

    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    Supplier<BiMap<Block, Block>> WAXED_BY_BLOCK = Suppliers.memoize(() ->
    {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.put(APBlocks.COPPER_NUB.get(), APBlocks.WAXED_COPPER_NUB.get());
        builder.put(APBlocks.EXPOSED_COPPER_NUB.get(), APBlocks.WAXED_EXPOSED_COPPER_NUB.get());
        builder.put(APBlocks.WEATHERED_COPPER_NUB.get(), APBlocks.WAXED_WEATHERED_COPPER_NUB.get());
        builder.put(APBlocks.OXIDIZED_COPPER_NUB.get(), APBlocks.WAXED_OXIDIZED_COPPER_NUB.get());
        return builder.build();
    });

    Supplier<BiMap<Block, Block>> UNWAXED_BY_BLOCK = Suppliers.memoize(() -> WAXED_BY_BLOCK.get().inverse());


    static Optional<Block> getPrevious(Block block) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(block));
    }
    static Optional<BlockState> getPrevious(BlockState stateIn) {
        return getPrevious(stateIn.getBlock()).map((block) -> block.withPropertiesOf(stateIn));
    }

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
    }
    default Optional<BlockState> getNext(BlockState state) {
        return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    static Optional<Block> getWaxed(Block block) {
        return Optional.ofNullable(WAXED_BY_BLOCK.get().get(block));
    }
    static Optional<BlockState> getWaxed(BlockState state) {
        return getWaxed(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    static Optional<Block> getUnWaxed(Block block) {
        return Optional.ofNullable(UNWAXED_BY_BLOCK.get().get(block));
    }
    static Optional<BlockState> getUnWaxed(BlockState state) {
        return getUnWaxed(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    static Block getFirst(Block baseBlock) {
        Block block = baseBlock;

        for(Block block1 = PREVIOUS_BY_BLOCK.get().get(block); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
            block = block1;
        }

        return block;
    }

    default float getChanceModifier() {
        return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }
        // (ender) fix this later [forgeism stripping]
//    static BlockState getToolModifiedState(ToolAction toolAction, BlockState state) {
//        if (ToolActions.AXE_SCRAPE == toolAction) return APWeatheringCopper.getPrevious(state).orElse(null);
//        if (ToolActions.AXE_WAX_OFF == toolAction) return APWeatheringCopper.getUnWaxed(state).orElse(null);
//        return null;
//    }

    static ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof HoneycombItem) {
            Optional<BlockState> waxed = APWeatheringCopper.getWaxed(state);
            if (waxed.isPresent()) {
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, stack);
                }
                if (!player.isCreative()) stack.shrink(1);
                //idk what these flags are
                world.setBlock(pos, waxed.get(), 11);
                //idk what this is but its from honeycomb code
                world.levelEvent(player, 3003, pos, 0);
                return ItemInteractionResult.sidedSuccess(world.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


}
