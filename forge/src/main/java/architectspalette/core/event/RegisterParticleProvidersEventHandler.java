package architectspalette.core.event;

import architectspalette.content.particles.WizardParticle;
import architectspalette.core.registry.ParticleRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static architectspalette.core.APConstants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterParticleProvidersEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
    }
}
