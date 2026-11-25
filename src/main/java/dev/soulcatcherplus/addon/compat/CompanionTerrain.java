// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** Blocks terrain damage from supported companion integrations and their attacks. */
public final class CompanionTerrain {
    // Retain the existing key for Twilight Forest attacks already saved in worlds.
    private static final String PROTECTED_ATTACK = "scp_compat_twilight_protected_attack";

    public static boolean isProtectedSoul(Entity entity) {
        if (entity == null || !CompanionBrain.isSoul(entity)) return false;
        var type = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        if (type == null) return false;
        String mod = type.getNamespace();
        return mod.equals("twilightforest") || mod.equals("aquamirae") || mod.equals("ob_aquamirae")
            || mod.equals("mowziesmobs");
    }

    public static boolean protectsTerrain(Entity source) {
        if (isProtectedSoul(source)) return true;
        if (source != null && source.getPersistentData().getBoolean(PROTECTED_ATTACK)) return true;
        return source instanceof Projectile projectile
            && isProtectedSoul(projectile.getOwner());
    }

    public static void protectAttack(Entity attack, Entity source) {
        if (isProtectedSoul(source)) attack.getPersistentData().putBoolean(PROTECTED_ATTACK, true);
    }

    @SubscribeEvent
    public static void rememberProjectileSource(EntityJoinLevelEvent event) {
        // Projectiles can outlive their shooter or have their owner changed by reflection.
        if (!event.getLevel().isClientSide && event.getEntity() instanceof Projectile projectile) {
            protectAttack(projectile, projectile.getOwner());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void griefing(EntityMobGriefingEvent event) {
        if (protectsTerrain(event.getEntity())) event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void explosion(ExplosionEvent.Detonate event) {
        var explosion = event.getExplosion();
        if (protectsTerrain(explosion.getExploder()) || protectsTerrain(explosion.getSourceMob())) {
            event.getAffectedBlocks().clear();
        }
    }

    private CompanionTerrain() {}
}
