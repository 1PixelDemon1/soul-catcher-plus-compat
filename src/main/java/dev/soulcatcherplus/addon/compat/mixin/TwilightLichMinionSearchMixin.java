// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.TwilightForestCompat;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.entity.boss.Lich;

@Pseudo
@Mixin(targets = "twilightforest.entity.monster.LichMinion", remap = false)
public abstract class TwilightLichMinionSearchMixin {
    @Group(name = "soulcatcherplus$lichMasters", min = 1, max = 1)
    @Redirect(method = "findNewMaster()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"), remap = false, require = 0)
    private List<Lich> soulcatcherplus$mastersOfficial(Level world, Class<Lich> type, AABB bounds) {
        return soulcatcherplus$masters(world, type, bounds);
    }

    @Group(name = "soulcatcherplus$lichMasters", min = 1, max = 1)
    @Redirect(method = "findNewMaster()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;m_45976_(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"), remap = false, require = 0)
    private List<Lich> soulcatcherplus$mastersSrg(Level world, Class<Lich> type, AABB bounds) {
        return soulcatcherplus$masters(world, type, bounds);
    }

    @org.spongepowered.asm.mixin.Unique
    private List<Lich> soulcatcherplus$masters(Level world, Class<Lich> type, AABB bounds) {
        return world.getEntitiesOfClass(type, bounds).stream()
            .filter(candidate -> TwilightForestCompat.allowsMaster((Mob)(Object)this, candidate)).collect(Collectors.toList());
    }
}
