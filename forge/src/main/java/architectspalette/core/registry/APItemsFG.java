package architectspalette.core.registry;

import architectspalette.core.registry.util.APBlockItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.RegistryObject;

import static architectspalette.core.registry.util.RegistryUtilsFG.createItem;

public class APItemsFG {
    public static Item resourceItem() { return new Item(new Item.Properties()); }

    public static final RegistryObject<Item> CHARCOAL_BLOCK = createItem("charcoal_block", () -> new APBlockItem(APBlocks.CHARCOAL_BLOCK.get(), new Item.Properties()).setBurnTime(1600), CreativeModeTabs.BUILDING_BLOCKS);

    public static final RegistryObject<Item> NETHER_BRASS_TORCH = createItem("nether_brass_torch", () -> new StandingAndWallBlockItem(APBlocks.NETHER_BRASS_TORCH.get(), APBlocks.NETHER_BRASS_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN), CreativeModeTabs.FUNCTIONAL_BLOCKS);

}