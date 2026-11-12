// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.companion.CompanionBrain;
import dev.soulcatcherplus.companion.SoulMinions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.entity.TFEntities;

/** Tantrums may spawn during hurt(), outside the creator's ordinary tick context. */
@Pseudo
@Mixin(targets = "twilightforest.entity.boss.UrGhastEntity", remap = false)
public abstract class TwilightUrGhastSummonMixin {
    @Group(name = "soulcatcherplus$urGhastChildren", min = 2, max = 2)
    @Redirect(method = "spawnMinionGhastsAt(III)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;addFreshEntity(Lnet/minecraft/entity/Entity;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$spawnOfficial(World world, Entity child) { return soulcatcherplus$spawn(world, child); }

    @Group(name = "soulcatcherplus$urGhastChildren", min = 2, max = 2)
    @Redirect(method = "spawnMinionGhastsAt(III)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;func_217376_c(Lnet/minecraft/entity/Entity;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$spawnSrg(World world, Entity child) { return soulcatcherplus$spawn(world, child); }

    @org.spongepowered.asm.mixin.Unique
    private boolean soulcatcherplus$spawn(World world, Entity child) {
        Entity creator = (Entity)(Object)this;
        if (CompanionBrain.isSoul(creator) && child instanceof MobEntity mob && child.getType() == TFEntities.mini_ghast) {
            SoulMinions.attach(mob, creator);
            if (mob.removed) return false;
        }
        return world.addFreshEntity(child);
    }
}
