package architectspalette.core.integration.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static architectspalette.core.ArchitectsPalette.rl;

public class CarveTotemTrigger extends SimpleCriterionTrigger<CarveTotemTrigger.Instance> {

    private static final ResourceLocation ID = rl("carve_totem");

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Instance::test);
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate context, DeserializationContext conditionsParser) {
        return new CarveTotemTrigger.Instance(context);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        public Instance(ContextAwarePredicate context) {
            super(CarveTotemTrigger.ID, context);
        }

        public static CarveTotemTrigger.Instance simple() {
            return new CarveTotemTrigger.Instance(ContextAwarePredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}
