// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import dev.soulcatcherplus.api.SoulAdapter;
import net.minecraft.world.entity.Mob;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;

/** Color and growth stage identify forms; each stored form retains its captured age. */
public final class IceAndFireDragonAdapter implements SoulAdapter {
    private static final int TICKS_PER_DAY = 24000, DAYS_PER_STAGE = 25, MAX_STAGE = 5;

    @Override public double followRange(Mob mob) { return 100; }

    @Override public CompoundTag variantData(CompoundTag snapshot) {
        CompoundTag form = new CompoundTag();
        form.putInt("Variant", snapshot.getInt("Variant"));
        int days = Math.max(0, snapshot.getInt("AgeTicks")) / TICKS_PER_DAY;
        form.putInt("DragonStage", Math.min(MAX_STAGE, days / DAYS_PER_STAGE + 1));
        return form;
    }

    @Override public CompoundTag previewData(CompoundTag snapshot) {
        CompoundTag preview = SoulAdapter.super.previewData(snapshot);
        for (String key : new String[]{"AgeTicks", "Gender"})
            if (snapshot.contains(key)) preview.put(key, snapshot.get(key).copy());
        return preview;
    }

    @Override public void prepareSummon(Mob mob, ServerPlayer owner) {
        EntityDragonBase dragon = (EntityDragonBase) mob;
        dragon.setModelDead(false);
        dragon.modelDeadProgress = 0;
        dragon.setDeathStage(0);
        dragon.setBreathingFire(false);
        dragon.setInSittingPose(false);
        dragon.setCommand(0);
        dragon.hasHomePosition = false;
        dragon.setCrystalBound(false);
        // A previously tamed captured dragon must not retain another player's owner.
        if (dragon.isTame()) dragon.setOwnerUUID(owner.getUUID());
    }

    @Override public void configureCompanion(Mob mob) {
        SoulAdapter.super.configureCompanion(mob);
        // Preserve the captured size after summon and on chunk/world reload.
        ((EntityDragonBase) mob).setAgingDisabled(true);
    }
}
