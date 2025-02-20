package de.melanx.yellowsnow.entities;

import de.melanx.yellowsnow.core.registration.ModEntities;
import de.melanx.yellowsnow.core.registration.ModItems;
import de.melanx.yellowsnow.data.DamageTypesProvider;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nonnull;

public class YellowSnowball extends ThrowableItemProjectile {

    public YellowSnowball(EntityType<YellowSnowball> entityType, Level level) {
        super(entityType, level);
    }

    public YellowSnowball(Level level, LivingEntity shooter) {
        super(ModEntities.yellowSnowball, shooter, level);
    }

    public YellowSnowball(Level level, double x, double y, double z) {
        super(ModEntities.yellowSnowball, x, y, z, level);
    }

    @Nonnull
    @Override
    protected Item getDefaultItem() {
        return ModItems.yellowSnowball;
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItem();
        return !itemstack.isEmpty() ? new ItemParticleOption(ParticleTypes.ITEM, itemstack) : null;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == Entity.BASE_SAFE_FALL_DISTANCE) {
            ParticleOptions particleOptions = this.getParticle();

            if (particleOptions == null) {
                return;
            }

            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        int extraDamage = entity instanceof Blaze ? 4 : 1;
        int damageAmount = this.random.nextInt(2) + 1 + extraDamage;
        entity.hurt(this.damageSources().source(DamageTypesProvider.PEE, this.getOwner()), damageAmount);
    }

    @Override
    protected void onHit(@Nonnull HitResult result) {
        super.onHit(result);
        if (this.level().isClientSide) {
            return;
        }

        this.level().broadcastEntityEvent(this, (byte) Entity.BASE_SAFE_FALL_DISTANCE);
        this.discard();
    }
}
