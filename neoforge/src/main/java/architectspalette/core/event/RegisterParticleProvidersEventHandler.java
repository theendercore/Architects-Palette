package architectspalette.core.event;

import architectspalette.content.particles.WizardParticle;
import architectspalette.core.registry.ParticleRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public class RegisterParticleProvidersEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
    }
}
