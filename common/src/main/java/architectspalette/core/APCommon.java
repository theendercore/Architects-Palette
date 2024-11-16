package architectspalette.core;

import architectspalette.core.integration.APCriterion;
import architectspalette.core.integration.APTrades;
import architectspalette.core.registry.*;

public class APCommon {
    public static void init() {

        APSounds.init();
        APItems.init();
        APBlocks.init();

        APFeatures.init();
        ParticleRegistry.init();

        APRecipes.init();
        APCriterion.init();
    }

    public static void lateInit() {
        APBlockProperties.registerFlammables();
        APTrades.registerTrades();
    }

    public static void initClient() {
        APRenderLayers.setupRenderLayers();
    }
}