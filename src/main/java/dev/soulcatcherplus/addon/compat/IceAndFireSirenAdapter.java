// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import dev.soulcatcherplus.api.SoulAdapter;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public final class IceAndFireSirenAdapter implements SoulAdapter {
    @Override public void prepareSummon(MobEntity mob, ServerPlayerEntity owner) {
        EntitySiren siren = (EntitySiren)mob;
        siren.setAggressive(false);
        siren.setSinging(false);
    }
}
