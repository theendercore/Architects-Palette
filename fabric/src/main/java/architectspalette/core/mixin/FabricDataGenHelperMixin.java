package architectspalette.core.mixin;

import architectspalette.core.integration.APVerticalSlabsCondition;
import architectspalette.core.integration.VerticalSlabs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(FabricDataGenHelper.class)
public class FabricDataGenHelperMixin {
    @Unique
    private static final String AP$NEO_CONDITIONS = "neoforge:conditions";

    @Inject(method = "addConditions(Lcom/google/gson/JsonObject;[Lnet/fabricmc/fabric/api/resource/conditions/v1/ResourceCondition;)V", at = @At("TAIL"), remap = false)
    private static void ap$addNeoConditions(JsonObject baseObject, ResourceCondition[] conditions, CallbackInfo ci) {
        if (!Arrays.stream(conditions).toList().contains(APVerticalSlabsCondition.INSTANCE)) return;
        if (baseObject.has(AP$NEO_CONDITIONS)) return;
        var obj = new JsonObject();
        obj.addProperty("type", VerticalSlabs.VERTICAL_SLABS_CONDITION.toString());
        var arr = new JsonArray();
        arr.add(obj);
        baseObject.add(AP$NEO_CONDITIONS, arr);
    }
}
