package architectspalette.core.registry;

import architectspalette.core.integration.VerticalSlabs;
import architectspalette.core.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;


public class APTab {
    @SuppressWarnings("unused")
    public static final Supplier<CreativeModeTab> AP_TAB = Services.REGISTRY.registerTab(
            "architects_palette",
            Component.translatable("itemGroup.architects_palette.everything"),
            () -> new ItemStack(APBlocks.CHISELED_ABYSSALINE_BRICKS.get()),
            (params, output) -> output.acceptAll(Services.REGISTRY.getModItems().stream()
                    .filter(VerticalSlabs::isVisible).map(ItemStack::new).toList())
    );

    public static void init() {
    }
}
