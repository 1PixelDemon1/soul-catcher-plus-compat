// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import dev.soulcatcherplus.api.SoulAdapter;
import net.minecraft.world.entity.Mob;
import net.minecraft.server.level.ServerPlayer;

public final class IceAndFireSirenAdapter implements SoulAdapter {
    @Override public void prepareSummon(Mob mob, ServerPlayer owner) {
        EntitySiren siren = (EntitySiren)mob;
        siren.setAggressive(false);
        siren.setSinging(false);
    }
}
