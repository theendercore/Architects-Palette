package architectspalette.core.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static architectspalette.core.events.ProjectileImpactEvent.projectileImpact;

@Debug(export = true)
@Mixin(Projectile.class)
public abstract class ProjectileMixin {

    @Shadow
    protected abstract boolean canHitEntity(Entity p_37250_);

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void changeDimension(CallbackInfo ci) {
        // (ender) if this is still pp then im an infant
        Projectile pp = (Projectile) (Object) this;
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(pp, this::canHitEntity);
        if (projectileImpact(pp, hitresult)) {
            ci.cancel();
            return;
        }
    }
}
