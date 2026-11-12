// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.LichEntity;
import twilightforest.entity.boss.LichMinionEntity;

/** The old native convenience constructor wrongly gives summoned minions the vanilla zombie type. */
@Pseudo
@Mixin(targets = "twilightforest.entity.ai.LichMinionsGoal", remap = false)
public abstract class TwilightLichSummonMixin {
    @Redirect(method = "spawnMinionAt()V", at = @At(value = "NEW",
        target = "(Lnet/minecraft/world/World;Ltwilightforest/entity/boss/LichEntity;)Ltwilightforest/entity/boss/LichMinionEntity;"), remap = false)
    private LichMinionEntity soulcatcherplus$typedMinion(World world, LichEntity lich) {
        if (!CompanionBrain.isSoul(lich)) return new LichMinionEntity(world, lich);
        LichMinionEntity minion = new LichMinionEntity(TFEntities.lich_minion, world);
        ((TwilightLichMinionAccess)minion).soulcatcherplus$setMaster(lich);
        return minion;
    }
}
