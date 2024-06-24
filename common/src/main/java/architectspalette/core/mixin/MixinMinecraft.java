package architectspalette.core.mixin;

import architectspalette.core.APConstants;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        
        APConstants.LOG.info("This line is printed by an example mod common mixin!");
        APConstants.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());
    }
}