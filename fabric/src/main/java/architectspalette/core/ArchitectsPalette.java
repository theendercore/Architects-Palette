package architectspalette.core;

import architectspalette.content.particles.WizardParticle;
import architectspalette.core.event.CreativeModeTabEventHandler;
import architectspalette.core.registry.MiscRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;

public class ArchitectsPalette implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        APCommon.initEarly();
        APCommon.init();

        CreativeModeTabEventHandler.init();
    }

    @Override
    public void onInitializeClient() {
        APCommon.initClient();

        registerParticleFactories();
    }

    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
    }
}
