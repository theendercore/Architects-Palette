package architectspalette.core.registry;

import architectspalette.core.platform.Services;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class APItems {
    public static void init(){}

//    public static final Supplier<Item> CHARCOAL_BLOCK = createItem("charcoal_block", () -> new APBlockItem(APBlocks.CHARCOAL_BLOCK.get(), new Item.Properties()).setBurnTime(1600), CreativeModeTabs.BUILDING_BLOCKS);

    public static final Supplier<Item> ALGAL_BLEND = createItem("algal_blend");
    public static final Supplier<Item> ALGAL_BRICK = createItem("algal_brick");

    public static final Supplier<Item> WITHERED_BONE = createItem("withered_bone");
    public static final Supplier<Item> ENTWINE_ROD = createItem("entwine_rod");
    public static final Supplier<Item> SUNMETAL_BLEND = createItem("sunmetal_blend");
    public static final Supplier<Item> SUNMETAL_BRICK = createItem("sunmetal_brick");

    public static final Supplier<Item> UNOBTANIUM = createItem("unobtanium");

    public static final Supplier<Item> BRASS_BLEND = createItem("nether_brass_blend");
    public static final Supplier<Item> NETHER_BRASS = createItem("nether_brass_ingot");
    public static final Supplier<Item> NETHER_BRASS_NUGGET = createItem("nether_brass_nugget");
//    public static final Supplier<Item> NETHER_BRASS_TORCH = createItem("nether_brass_torch", () -> new StandingAndWallBlockItem(APBlocks.NETHER_BRASS_TORCH.get(), APBlocks.NETHER_BRASS_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    public static final Supplier<Item> WARDSTONE_BLEND = createItem("wardstone_blend");
    public static final Supplier<Item> WARDSTONE_BRICK = createItem("wardstone_brick");

    public static final Supplier<Item> ORACLE_JELLY = createItem("oracle_jelly");
    public static final Supplier<Item> CEREBRAL_PLATE = createItem("cerebral_plate");

    public static final Supplier<Item> createItem(String name) {
        return Services.REGISTRY.registerItem(name, APItems::resourceItem);
    }

    public static Item resourceItem() {
        return new Item(new Item.Properties());
    }
}