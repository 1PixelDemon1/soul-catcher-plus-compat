// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import dev.soulcatcherplus.api.SoulAdapters;
import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** Optional Ice and Fire 2.1.13 integration; loaded only when that mod is present. */
public final class IceAndFireCompat {
    public static void register() {
        for (String dragon : new String[]{"fire_dragon", "ice_dragon", "lightning_dragon"})
            SoulAdapters.register(new ResourceLocation("iceandfire", dragon), new IceAndFireDragonAdapter());
        SoulAdapters.register(new ResourceLocation("iceandfire", "hydra"), new IceAndFireHydraAdapter());
        SoulAdapters.register(new ResourceLocation("iceandfire", "siren"), new IceAndFireSirenAdapter());
        MinecraftForge.EVENT_BUS.register(IceAndFireCompat.class);
    }

    public static boolean isSoul(Entity entity) {
        if (entity == null || !CompanionBrain.isSoul(entity)) return false;
        ResourceLocation type = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        return type != null && type.getNamespace().equals("iceandfire");
    }

    @SubscribeEvent public static void griefing(EntityMobGriefingEvent event) {
        // Dragon charge damage radius depends on this result; its terrain paths have scoped hooks.
        if (!(event.getEntity() instanceof EntityDragonBase) && isSoul(event.getEntity())) event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent public static void blockDamage(GenericGriefEvent event) {
        // The worm's event also gates entity damage; leave its explosion alive.
        if ((event.getEntity() instanceof EntityDragonBase || event.getEntity() instanceof EntityCyclops)
                && isSoul(event.getEntity())) event.setCanceled(true);
    }

    @SubscribeEvent public static void explosion(ExplosionEvent.Detonate event) {
        if (isSoul(event.getExplosion().getIndirectSourceEntity())) event.getAffectedBlocks().clear();
    }

    private IceAndFireCompat() {}
}
