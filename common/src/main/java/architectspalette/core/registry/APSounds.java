package architectspalette.core.registry;

import architectspalette.core.platform.Services;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

import static architectspalette.core.APConstants.rl;

@SuppressWarnings("deprecation")
public class APSounds {
    public static void init(){}

    private static Supplier<SoundEvent> register(String key) {
        return Services.REGISTRY.registerSoundEvent(key, () -> SoundEvent.createVariableRangeEvent(rl(key)));
    }

    public static final Supplier<SoundEvent> BLOCK_ENTWINE_PLACE = register("block.entwine.place");
    public static final Supplier<SoundEvent> BLOCK_ENTWINE_HIT = register("block.entwine.hit");

    public static final Supplier<SoundEvent> ITEM_WARPS = register("block.warping.item_warps");
    public static final Supplier<SoundEvent> CAGE_LANTERN_TOGGLE_ON = register("block.cage_lantern.toggle_on");
    public static final Supplier<SoundEvent> CAGE_LANTERN_TOGGLE_OFF = register("block.cage_lantern.toggle_off");

    public static final Supplier<SoundEvent> WIZARD_BLAST = register("block.wizard_deflect");

    public static final Supplier<SoundEvent> HADALINE_ACTIVATE = register("block.chiseled_hadaline.activate");

    public static class APSoundTypes {
        public static final SoundType ENTWINE = new LazySoundType(
                1.0F, 1.0F,
                new LazyLoadedValue<>(() -> SoundEvents.DEEPSLATE_TILES_BREAK),
                new LazyLoadedValue<>(() -> SoundEvents.GLASS_STEP),
                new LazyLoadedValue<>(BLOCK_ENTWINE_PLACE),
                new LazyLoadedValue<>(BLOCK_ENTWINE_HIT),
                new LazyLoadedValue<>(() -> SoundEvents.GLASS_FALL)
        );

        public static final SoundType ENDER_PEARL = new SoundType(
                1.0F, 2.0F,
                SoundEvents.SHROOMLIGHT_BREAK,
                SoundEvents.SHROOMLIGHT_STEP,
                SoundEvents.SHROOMLIGHT_PLACE,
                SoundEvents.SHROOMLIGHT_HIT,
                SoundEvents.SHROOMLIGHT_FALL
        );
    }

    private static class LazySoundType extends SoundType {
        private final LazyLoadedValue<SoundEvent> breakSound;
        private final LazyLoadedValue<SoundEvent> stepSound;
        private final LazyLoadedValue<SoundEvent> placeSound;
        private final LazyLoadedValue<SoundEvent> hitSound;
        private final LazyLoadedValue<SoundEvent> fallSound;

        public LazySoundType(float volumeIn, float pitchIn, LazyLoadedValue<SoundEvent> breakSoundIn, LazyLoadedValue<SoundEvent> stepSoundIn, LazyLoadedValue<SoundEvent> placeSoundIn, LazyLoadedValue<SoundEvent> hitSoundIn, LazyLoadedValue<SoundEvent> fallSoundIn) {
            // shut up java i dont care about the original constructor i just want this to be a sound type
            super(volumeIn, pitchIn, SoundEvents.STONE_BREAK, SoundEvents.STONE_STEP, SoundEvents.STONE_PLACE, SoundEvents.STONE_HIT, SoundEvents.STONE_FALL);
            // now we're talking
            this.breakSound = breakSoundIn;
            this.stepSound = stepSoundIn;
            this.placeSound = placeSoundIn;
            this.hitSound = hitSoundIn;
            this.fallSound = fallSoundIn;
        }

        public float getVolume() {
            return this.volume;
        }

        public float getPitch() {
            return this.pitch;
        }

        public SoundEvent getBreakSound() {
            return this.breakSound.get();
        }

        public SoundEvent getStepSound() {
            return this.stepSound.get();
        }

        public SoundEvent getPlaceSound() {
            return this.placeSound.get();
        }

        public SoundEvent getHitSound() {
            return this.hitSound.get();
        }

        public SoundEvent getFallSound() {
            return this.fallSound.get();
        }

    }
}
