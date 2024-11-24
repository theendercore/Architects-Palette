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

@Mixin(Projectile.class)
public abstract class ProjectileMixin {

    @Shadow
    protected abstract boolean canHitEntity(Entity p_37250_);

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void bounce(CallbackInfo ci) {
        Projectile projectile = (Projectile) (Object) this;
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(projectile, this::canHitEntity);
        if (projectileImpact(projectile, hitresult)) {
            ci.cancel();
            return;
        }
    }
}
