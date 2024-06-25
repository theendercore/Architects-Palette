package architectspalette.core.registry;

import net.minecraft.core.Direction;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.registries.RegistryObject;

import static architectspalette.core.registry.util.RegistryUtilsFG.createItem;

public class APItemsFG {
    public static Item resourceItem() { return new Item(new Item.Properties()); }

    public static final RegistryObject<Item> NETHER_BRASS_TORCH = createItem("nether_brass_torch", () -> new StandingAndWallBlockItem(APBlocksFG.NETHER_BRASS_TORCH.get(), APBlocksFG.NETHER_BRASS_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN), CreativeModeTabs.FUNCTIONAL_BLOCKS);

}