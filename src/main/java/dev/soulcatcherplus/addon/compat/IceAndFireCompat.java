// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import dev.soulcatcherplus.api.SoulAdapters;
import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** Optional Ice and Fire 2.1.12 integration; loaded only when that mod is present. */
public final class IceAndFireCompat {
    public static void register() {
        for (String dragon : new String[]{"fire_dragon", "ice_dragon", "lightning_dragon"})
            SoulAdapters.register(new ResourceLocation("iceandfire", dragon), new IceAndFireDragonAdapter());
        SoulAdapters.register(new ResourceLocation("iceandfire", "hydra"), new IceAndFireHydraAdapter());
        MinecraftForge.EVENT_BUS.register(IceAndFireCompat.class);
    }

    public static boolean isSoul(Entity entity) {
        if (entity == null || !CompanionBrain.isSoul(entity)) return false;
        ResourceLocation type = ForgeRegistries.ENTITIES.getKey(entity.getType());
        return type != null && type.getNamespace().equals("iceandfire");
    }

    @SubscribeEvent public static void griefing(EntityMobGriefingEvent event) {
        if (isSoul(event.getEntity())) event.setResult(Event.Result.DENY);
    }

    @SubscribeEvent public static void blockDamage(GenericGriefEvent event) {
        // The worm's event also gates entity damage; leave its explosion alive.
        if ((event.getEntityLiving() instanceof EntityDragonBase || event.getEntityLiving() instanceof EntityCyclops)
                && isSoul(event.getEntityLiving())) event.setCanceled(true);
    }

    @SubscribeEvent public static void explosion(ExplosionEvent.Detonate event) {
        if (isSoul(event.getExplosion().getSourceMob())) event.getAffectedBlocks().clear();
    }

    private IceAndFireCompat() {}
}
