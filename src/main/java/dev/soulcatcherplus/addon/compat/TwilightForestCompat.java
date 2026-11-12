// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import dev.soulcatcherplus.api.SoulAdapter;
import dev.soulcatcherplus.api.SoulAdapters;
import dev.soulcatcherplus.companion.CompanionBrain;
import dev.soulcatcherplus.companion.SoulMinions;
import dev.soulcatcherplus.addon.compat.mixin.TwilightLichMinionAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.LichEntity;

/** Optional Twilight Forest 4.0.870 links: exact creators, never nearby adoption. */
public final class TwilightForestCompat {
    public static void register() {
        registerTickSummoner("snow_queen", TFEntities.ice_crystal);
        registerTickSummoner("swarm_spider", TFEntities.swarm_spider);
        registerTickSummoner("tower_broodling", TFEntities.tower_broodling);
        SoulAdapters.register(id("lich"), new LichAdapter());
        SoulAdapters.register(id("lich_minion"), new LichMinionAdapter());
        // Ur-Ghast uses a native spawn-site hook: damage can trigger its summon outside its own tick.
    }

    private static ResourceLocation id(String path) { return new ResourceLocation("twilightforest", path); }

    private static void registerTickSummoner(String parent, EntityType<?> child) {
        SoulAdapters.register(id(parent), new SoulAdapter() {
            @Override public boolean inheritsTickSpawn(MobEntity summoner, EntityType<?> childType) {
                return childType == child;
            }
        });
    }

    /** Native orphan searches cannot turn a wild entity into a soul's new follower. */
    public static boolean allowsMaster(MobEntity child, LichEntity candidate) {
        if (!CompanionBrain.isSoul(child)) return !CompanionBrain.isSoul(candidate);
        if (!SoulMinions.isMinion(child) || !CompanionBrain.isSoul(candidate) || SoulMinions.isMinion(candidate)) return false;
        return candidate.getUUID().equals(child.getPersistentData().getUUID(SoulMinions.ROOT))
            && CompanionBrain.owner(child).equals(CompanionBrain.owner(candidate))
            && CompanionBrain.activeEntry(child) != null
            && CompanionBrain.activeEntry(child) == CompanionBrain.activeEntry(candidate);
    }

    /** A phase change must not delete another player's clones or clones from a wild encounter. */
    public static boolean mayDismissClone(LichEntity lich, LichEntity candidate) {
        if (!CompanionBrain.isSoul(lich)) return !CompanionBrain.isSoul(candidate);
        return candidate.getMasterLich() == lich && SoulMinions.isMinion(candidate)
            && candidate.getPersistentData().getUUID(SoulMinions.ROOT).equals(lich.getUUID())
            && CompanionBrain.owner(candidate).equals(CompanionBrain.owner(lich));
    }

    private static final class LichAdapter implements SoulAdapter {
        @Override public Entity summoner(MobEntity mob) { return ((LichEntity)mob).getMasterLich(); }

        @Override public void restoreSummoner(MobEntity minion, MobEntity root) {
            if (root instanceof LichEntity lich && ((LichEntity)minion).getMasterLich() != lich)
                ((LichEntity)minion).setMaster(lich);
        }
    }

    private static final class LichMinionAdapter implements SoulAdapter {
        @Override public Entity summoner(MobEntity mob) { return ((TwilightLichMinionAccess)mob).soulcatcherplus$getMaster(); }

        @Override public void restoreSummoner(MobEntity minion, MobEntity root) {
            if (root instanceof LichEntity lich) {
                TwilightLichMinionAccess link = (TwilightLichMinionAccess)minion;
                if (link.soulcatcherplus$getMaster() != lich) link.soulcatcherplus$setMaster(lich);
            }
        }
    }

    private TwilightForestCompat() {}
}
