// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.TwilightForestCompat;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.entity.boss.LichEntity;

@Pseudo
@Mixin(targets = "twilightforest.entity.boss.LichMinionEntity", remap = false)
public abstract class TwilightLichMinionSearchMixin {
    @Group(name = "soulcatcherplus$lichMasters", min = 1, max = 1)
    @Redirect(method = "findNewMaster()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;"), remap = false, require = 0)
    private List<LichEntity> soulcatcherplus$mastersOfficial(World world, Class<LichEntity> type, AxisAlignedBB bounds) {
        return soulcatcherplus$masters(world, type, bounds);
    }

    @Group(name = "soulcatcherplus$lichMasters", min = 1, max = 1)
    @Redirect(method = "findNewMaster()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;func_217357_a(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;"), remap = false, require = 0)
    private List<LichEntity> soulcatcherplus$mastersSrg(World world, Class<LichEntity> type, AxisAlignedBB bounds) {
        return soulcatcherplus$masters(world, type, bounds);
    }

    @org.spongepowered.asm.mixin.Unique
    private List<LichEntity> soulcatcherplus$masters(World world, Class<LichEntity> type, AxisAlignedBB bounds) {
        return world.getEntitiesOfClass(type, bounds).stream()
            .filter(candidate -> TwilightForestCompat.allowsMaster((MobEntity)(Object)this, candidate)).collect(Collectors.toList());
    }
}
