package architectspalette.core.registry;

import architectspalette.core.platform.Services;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.modLoc;

public class MiscRegistry {

    // Tags
    public static final TagKey<Block> CRYSTAL_REPLACEABLE = blockTag("crystal_formation_replaceable");
    public static final TagKey<Block> GREEN_FIRE_SUPPORTING = blockTag("green_fire_supporting");
    public static final TagKey<Block> WIZARD_BLOCKS = blockTag("wizard_blocks");
    public static final TagKey<Block> NUBS = blockTag("nubs");

    public static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, modLoc(name));
    }

    //Particles
    public static final Supplier<SimpleParticleType> GREEN_FLAME = Services.REGISTRY.registerSimpleParticleType("green_flame");
    public static final Supplier<SimpleParticleType> WIZARDLY_DEFENSE_BLAST = Services.REGISTRY.registerSimpleParticleType("bounce");

}
