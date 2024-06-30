package architectspalette.core.registry;

import architectspalette.core.platform.Services;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class MiscRegistry {
    public static void init(){}
    //Particles
    public static final Supplier<SimpleParticleType> GREEN_FLAME = Services.REGISTRY.registerSimpleParticleType("green_flame");
    public static final Supplier<SimpleParticleType> WIZARDLY_DEFENSE_BLAST = Services.REGISTRY.registerSimpleParticleType("bounce");

}
