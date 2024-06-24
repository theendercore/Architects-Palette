package architectspalette.core.integration.advancement;

import architectspalette.core.integration.APCriterion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class CarveTotemTrigger extends SimpleCriterionTrigger<CarveTotemTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, TriggerInstance::test);
    }


    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((p_325204_) -> {
            return p_325204_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
            ).apply(p_325204_, TriggerInstance::new);
        });

        public static Criterion<TriggerInstance> simple(){
            return APCriterion.CARVE_TOTEM.createCriterion(new TriggerInstance(Optional.empty()));
        }

        public boolean test() {
            return true;
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }
}
