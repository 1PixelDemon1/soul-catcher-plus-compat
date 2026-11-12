// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.IceAndFireCompat;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
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
    @Redirect(method = {"aiStep", "func_70636_d"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;explode(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/Explosion$Mode;)Lnet/minecraft/world/Explosion;"),
        remap = false, require = 0)
    private Explosion soulcatcherplus$explodeOfficial(World world, Entity source, double x, double y, double z,
            float strength, boolean fire, Explosion.Mode mode) {
        return soulcatcherplus$explode(world, source, x, y, z, strength, fire, mode);
    }

    @Group(name = "soulcatcherplus$wormExplosion", min = 1, max = 1)
    @Redirect(method = {"aiStep", "func_70636_d"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;func_217398_a(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/Explosion$Mode;)Lnet/minecraft/world/Explosion;"),
        remap = false, require = 0)
    private Explosion soulcatcherplus$explodeSrg(World world, Entity source, double x, double y, double z,
            float strength, boolean fire, Explosion.Mode mode) {
        return soulcatcherplus$explode(world, source, x, y, z, strength, fire, mode);
    }

    @org.spongepowered.asm.mixin.Unique
    private Explosion soulcatcherplus$explode(World world, Entity source, double x, double y, double z,
            float strength, boolean fire, Explosion.Mode mode) {
        boolean soul = IceAndFireCompat.isSoul((Entity)(Object)this);
        return world.explode(source, x, y, z, strength, fire, soul ? Explosion.Mode.NONE : mode);
    }
}
