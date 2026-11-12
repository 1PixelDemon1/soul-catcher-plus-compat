// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.companion.CompanionBrain;
import dev.soulcatcherplus.companion.SoulMinions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.init.TFEntities;

/** Tantrums may spawn during hurt(), outside the creator's ordinary tick context. */
@Pseudo
@Mixin(targets = "twilightforest.entity.boss.UrGhast", remap = false)
public abstract class TwilightUrGhastSummonMixin {
    @Group(name = "soulcatcherplus$urGhastChildren", min = 2, max = 2)
    @Redirect(method = "spawnMinionGhastsAt(III)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$spawnOfficial(Level world, Entity child) { return soulcatcherplus$spawn(world, child); }

    @Group(name = "soulcatcherplus$urGhastChildren", min = 2, max = 2)
    @Redirect(method = "spawnMinionGhastsAt(III)V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;m_7967_(Lnet/minecraft/world/entity/Entity;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$spawnSrg(Level world, Entity child) { return soulcatcherplus$spawn(world, child); }

    @org.spongepowered.asm.mixin.Unique
    private boolean soulcatcherplus$spawn(Level world, Entity child) {
        Entity creator = (Entity)(Object)this;
        if (CompanionBrain.isSoul(creator) && child instanceof Mob mob && child.getType() == TFEntities.CARMINITE_GHASTLING.get()) {
            SoulMinions.attach(mob, creator);
            if (mob.isRemoved()) return false;
        }
        return world.addFreshEntity(child);
    }
}
