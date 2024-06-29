package architectspalette.core.event;

import architectspalette.content.particles.WizardParticle;
import architectspalette.core.registry.MiscRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;

public class RegisterParticleProvidersEventHandler {
    public static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
    }
}
