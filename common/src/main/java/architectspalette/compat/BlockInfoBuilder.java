package architectspalette.compat;

import architectspalette.core.integration.VerticalSlabs;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.util.BlockNode;
import architectspalette.core.registry.util.StoneBlockSet;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.APConstants.rl;

public class BlockInfoBuilder {
    public interface InfoRecipeRegistrar {
        void register(List<ItemStack> ingredients, Component text, ResourceLocation id);
    }

    public final List<Block> blocks = new ArrayList<>();
    public final InfoRecipeRegistrar registrar;

    public BlockInfoBuilder(InfoRecipeRegistrar registrar) {
        this.registrar = registrar;
    }

    public BlockInfoBuilder add(BlockNode... nodes) {
        for (BlockNode node : nodes)
            node.forEach((n) -> blocks.add(n.get()));
        return this;
    }

    public BlockInfoBuilder add(StoneBlockSet... sets) {
        for (StoneBlockSet set : sets) {
            set.forEach(blocks::add);
        }
        return this;
    }

    public BlockInfoBuilder add(Predicate<Block> filter) {
        for (Block entry : Services.REGISTRY.getModBlocks()) {
            if (filter.test(entry)) blocks.add(entry);
        }
        return this;
    }

    @SafeVarargs
    public final BlockInfoBuilder add(Supplier<? extends Block>... blockList) {
        for (Supplier<? extends Block> block : blockList) {
            blocks.add(block.get());
        }
        return this;
    }

    public void registerInfo(String infoString) {
        var items = blocks.stream().filter(VerticalSlabs::isVisible).map(ItemStack::new).toList();
        var text = Component.translatable(MOD_ID + ".info." + infoString);
        registrar.register(items, text, rl("/info/" + infoString));
        blocks.clear();
    }
}