// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.IceAndFireCompat;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;

/** TNT-fed worms attribute the explosion to the thrower, so inspect the worm at its call site. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.EntityDeathWorm", remap = false)
public abstract class IceAndFireDeathWormMixin {
    @Group(name = "soulcatcherplus$wormExplosion", min = 1, max = 1)
    @Redirect(method = {"aiStep", "m_8107_"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"),
        remap = false, require = 0)
    private Explosion soulcatcherplus$explodeOfficial(Level world, Entity source, double x, double y, double z,
            float strength, boolean fire, Level.ExplosionInteraction mode) {
        return soulcatcherplus$explode(world, source, x, y, z, strength, fire, mode);
    }

    @Group(name = "soulcatcherplus$wormExplosion", min = 1, max = 1)
    @Redirect(method = {"aiStep", "m_8107_"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;m_255391_(Lnet/minecraft/world/entity/Entity;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"),
        remap = false, require = 0)
    private Explosion soulcatcherplus$explodeSrg(Level world, Entity source, double x, double y, double z,
            float strength, boolean fire, Level.ExplosionInteraction mode) {
        return soulcatcherplus$explode(world, source, x, y, z, strength, fire, mode);
    }

    @org.spongepowered.asm.mixin.Unique
    private Explosion soulcatcherplus$explode(Level world, Entity source, double x, double y, double z,
            float strength, boolean fire, Level.ExplosionInteraction mode) {
        boolean soul = IceAndFireCompat.isSoul((Entity)(Object)this);
        return world.explode(source, x, y, z, strength, fire, soul ? Level.ExplosionInteraction.NONE : mode);
    }
}
