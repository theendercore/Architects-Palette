package architectspalette.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static architectspalette.core.APConstants.MOD_ID;
import static architectspalette.core.ArchitectsPalette.rl;

public class MiscRegistry {

    // Tags
    public static final TagKey<Block> CRYSTAL_REPLACEABLE = blockTag("crystal_formation_replaceable");
    public static final TagKey<Block> GREEN_FIRE_SUPPORTING = blockTag("green_fire_supporting");
    public static final TagKey<Block> WIZARD_BLOCKS = blockTag("wizard_blocks");
    public static final TagKey<Block> NUBS = blockTag("nubs");

    public static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, rl(name));
    }

    //Particles
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final RegistryObject<SimpleParticleType> GREEN_FLAME = PARTICLE_TYPES.register("green_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> WIZARDLY_DEFENSE_BLAST = PARTICLE_TYPES.register("bounce", () -> new SimpleParticleType(false));

}
