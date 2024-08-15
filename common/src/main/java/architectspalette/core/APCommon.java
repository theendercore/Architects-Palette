package architectspalette.core;

import architectspalette.core.integration.APCriterion;
import architectspalette.core.integration.APTrades;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.*;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class APCommon {

    public static void initConfig() {
        // (ender) you will never guess what this has to be its own thing :)
        Services.REGISTRY.resisterConfig();
    }

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        APSounds.init();
        APItems.init();
        APBlocks.init();

        APFeatures.init();
        ParticleRegistry.init();

        APRecipes.init();

        if (Services.PLATFORM.isModLoaded(APConstants.MOD_ID)) {
            APConstants.LOGGER.info("I am loaded my self");
        }
    }

    public static void lateInit() {
        APCriterion.init();
        APBlockProperties.registerFlammables();
        APTrades.registerTrades();
    }

    public static void initClient() {
        APRenderLayers.setupRenderLayers();
    }
}