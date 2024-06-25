package architectspalette.core;

import architectspalette.content.particles.WizardParticle;
import architectspalette.core.registry.MiscRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;

public class ArchitectsPaletteClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        APCommonClass.initClient();

        registerParticleFactories();
    }

    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
    }
}