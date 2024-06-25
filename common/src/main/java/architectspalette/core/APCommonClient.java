package architectspalette.core;

import architectspalette.core.registry.*;


public class APCommonClient {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {
//        APRenderLayers.setupRenderLayers();
    }
}