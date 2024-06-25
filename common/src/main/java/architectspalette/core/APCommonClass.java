package architectspalette.core;

import architectspalette.core.integration.APCriterion;
import architectspalette.core.platform.Services;
import architectspalette.core.registry.APItems;
import architectspalette.core.registry.APRecipes;
import architectspalette.core.registry.APSounds;
import architectspalette.core.registry.MiscRegistry;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class APCommonClass {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Services.REGISTRY.resisterConfig();

        APSounds.init();
        APItems.init();
        MiscRegistry.init();

        APRecipes.init();

        if (Services.PLATFORM.isModLoaded(APConstants.MOD_ID)) {
            APConstants.LOGGER.info("Hello to examplemod");
        }
    }
    public static void earlyInit() {
        APCriterion.init();
    }


    // (ender) I have no idea if this can go in the same calls as init, but I will see :)
    public static void initClient() {

    }
}