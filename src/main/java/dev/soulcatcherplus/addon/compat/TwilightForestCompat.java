// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import dev.soulcatcherplus.api.SoulAdapter;
import dev.soulcatcherplus.api.SoulAdapters;
import dev.soulcatcherplus.companion.CompanionBrain;
import dev.soulcatcherplus.companion.SoulMinions;
import twilightforest.entity.monster.LichMinion;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;
import twilightforest.init.TFEntities;
import twilightforest.entity.boss.Lich;

/** Optional Twilight Forest 4.2.1518 links: exact creators, never nearby adoption. */
public final class TwilightForestCompat {
    public static void register() {
        registerTickSummoner("snow_queen", TFEntities.ICE_CRYSTAL.get());
        registerTickSummoner("swarm_spider", TFEntities.SWARM_SPIDER.get());
        registerTickSummoner("carminite_broodling", TFEntities.CARMINITE_BROODLING.get());
        SoulAdapters.register(id("lich"), new LichAdapter());
        SoulAdapters.register(id("lich_minion"), new LichMinionAdapter());
        // Ur-Ghast uses a native spawn-site hook: damage can trigger its summon outside its own tick.
    }

    private static ResourceLocation id(String path) { return new ResourceLocation("twilightforest", path); }

    private static void registerTickSummoner(String parent, EntityType<?> child) {
        SoulAdapters.register(id(parent), new SoulAdapter() {
            @Override public boolean inheritsTickSpawn(Mob summoner, EntityType<?> childType) {
                return childType == child;
            }
        });
    }

    /** Native orphan searches cannot turn a wild entity into a soul's new follower. */
    public static boolean allowsMaster(Mob child, Lich candidate) {
        if (!CompanionBrain.isSoul(child)) return !CompanionBrain.isSoul(candidate);
        if (!SoulMinions.isMinion(child) || !CompanionBrain.isSoul(candidate) || SoulMinions.isMinion(candidate)) return false;
        return candidate.getUUID().equals(child.getPersistentData().getUUID(SoulMinions.ROOT))
            && CompanionBrain.owner(child).equals(CompanionBrain.owner(candidate))
            && CompanionBrain.activeEntry(child) != null
            && CompanionBrain.activeEntry(child) == CompanionBrain.activeEntry(candidate);
    }

    /** A phase change must not delete another player's clones or clones from a wild encounter. */
    public static boolean mayDismissClone(Lich lich, Lich candidate) {
        if (!CompanionBrain.isSoul(lich)) return !CompanionBrain.isSoul(candidate);
        return candidate.getMasterLich() == lich && SoulMinions.isMinion(candidate)
            && candidate.getPersistentData().getUUID(SoulMinions.ROOT).equals(lich.getUUID())
            && CompanionBrain.owner(candidate).equals(CompanionBrain.owner(lich));
    }

    private static final class LichAdapter implements SoulAdapter {
        @Override public Entity summoner(Mob mob) { return ((Lich)mob).getMasterLich(); }

        @Override public void restoreSummoner(Mob minion, Mob root) {
            if (root instanceof Lich lich && ((Lich)minion).getMasterLich() != lich)
                ((Lich)minion).setMaster(lich);
        }
    }

    private static final class LichMinionAdapter implements SoulAdapter {
        @Override public Entity summoner(Mob mob) { return ((LichMinion)mob).master; }

        @Override public void restoreSummoner(Mob minion, Mob root) {
            if (root instanceof Lich lich) {
                LichMinion link = (LichMinion)minion;
                if (link.master != lich) link.master = lich;
            }
        }
    }

    private TwilightForestCompat() {}
}
