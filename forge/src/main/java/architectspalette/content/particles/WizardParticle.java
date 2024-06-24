package architectspalette.content.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WizardParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private final Quaternionf rotation;
    //With no rotation, particles face towards -Z
    private static final Vec3 DEFAULT_PARTICLE_DIRECTION = new Vec3(0, 0, -1);

    protected WizardParticle(ClientLevel level, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        super(level, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
        xd = 0;
        yd = 0;
        zd = 0;
        sprites = sprite;
        setSpriteFromAge(sprite);
        lifetime = 6;
        scale(2);

        Vec3 facing = new Vec3(xSpeed, ySpeed, zSpeed).normalize();
        Vec3 def = DEFAULT_PARTICLE_DIRECTION;
        Vec3 cross = def.cross(facing).normalize();

        double rotationAngle = Math.acos(def.dot(facing));
        Vector3f rotationAxis;
        //Fixes particles facing towards +-Z
        if (cross.length() == 0) {
            rotationAxis = new Vector3f(1, 0, 0);
        }
        else {
            rotationAxis = new Vector3f((float) cross.x, (float) cross.y, (float) cross.z);
        }
        rotation = new Quaternionf(new AxisAngle4f((float) rotationAngle, rotationAxis));
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        Vec3 pos = camera.getPosition();
        Vector3f center = new Vector3f(
                (float) (Mth.lerp(partialTicks, xo, x) - pos.x),
                (float) (Mth.lerp(partialTicks, yo, y) - pos.y),
                (float) (Mth.lerp(partialTicks, zo, z) - pos.z)
        );
        Quaternionf quat;
        if (roll == 0) {
            quat = rotation;
        }
        else {
            quat = new Quaternionf(rotation);
            quat.rotateZ(Mth.lerp(partialTicks, oRoll, roll));
        }

        //I could clean this up, but I don't want to. It's just copied from the default particle.
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float size = this.getQuadSize(partialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quat);
            vector3f.mul(size);
            vector3f.add(center);
        }

        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int j = this.getLightColor(partialTicks);
        // (ender) i hope this works
        vertexConsumer.addVertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).setUv(f8, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
        vertexConsumer.addVertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).setUv(f8, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
        vertexConsumer.addVertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).setUv(f7, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);
        vertexConsumer.addVertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).setUv(f7, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(j);

    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_106827_) {
            this.sprite = p_106827_;
        }

        public Particle createParticle(SimpleParticleType p_106838_, ClientLevel p_106839_, double p_106840_, double p_106841_, double p_106842_, double p_106843_, double p_106844_, double p_106845_) {
            return new WizardParticle(p_106839_, p_106840_, p_106841_, p_106842_, p_106843_, p_106844_, p_106845_, sprite);
        }
    }

}
