package architectspalette.core.registry;

import architectspalette.core.registry.util.BlockNode;


import java.util.stream.Stream;

import static architectspalette.core.registry.APBlockProperties.registerFlammable;
import static architectspalette.core.registry.APBlocks.*;
import static architectspalette.core.registry.APBlocksFG.*;

public class APBlockPropertiesFG {
	public static void registerFlammables() {
		// Logs & Coal: 5, 5
		// Planks: 5, 20
		// Leaves & Wool: 30, 60
		// Plants: 60, 100
		registerFlammable(TWISTED_FENCE.get(), 5, 20);
		TWISTED_PLANKS.registerFlammable(5, 20);
		Stream.of(GRINNING_ACACIA_TOTEM, PLACID_ACACIA_TOTEM, SHOCKED_ACACIA_TOTEM, BLANK_ACACIA_TOTEM,
				TWISTED_LOG, STRIPPED_TWISTED_LOG, TWISTED_WOOD, STRIPPED_TWISTED_WOOD,
				SPOOL, CHARCOAL_BLOCK,
				OAK_RAILING, BIRCH_RAILING, SPRUCE_RAILING,
				ACACIA_RAILING, DARK_OAK_RAILING, JUNGLE_RAILING,
				TWISTED_RAILING, MANGROVE_RAILING
		).forEach((t) -> {
			registerFlammable(t.get(), 5, 5);
		});
		for (BlockNode node : boards) {
			node.forEach((n) -> {
				registerFlammable(n.get(), 5, 20);
			});
		}
	}
}