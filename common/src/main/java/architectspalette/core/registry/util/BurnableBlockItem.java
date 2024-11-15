package architectspalette.core.registry.util;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class BurnableBlockItem extends BlockItem {
    private Integer burnTime = -1;

    public BurnableBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public BurnableBlockItem setBurnTime(int ticks) {
        this.burnTime = ticks;
        return this;
    }

    // (ender) this is here for forge other is for fabric
    public int getBurnTime(ItemStack itemStack, RecipeType<?> type) {
        return this.burnTime;
    }
    public int getBurnTime() {
        return this.burnTime;
    }
}
