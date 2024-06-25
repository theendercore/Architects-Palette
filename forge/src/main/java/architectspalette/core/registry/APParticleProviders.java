package architectspalette.core.registry;

import architectspalette.content.particles.WizardParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static architectspalette.core.APConstants.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class APParticleProviders {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(MiscRegistry.GREEN_FLAME.get(), FlameParticle.Provider::new);
        event.registerSpriteSet(MiscRegistry.WIZARDLY_DEFENSE_BLAST.get(), WizardParticle.Provider::new);
    }
}
