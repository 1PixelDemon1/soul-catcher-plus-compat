// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;
import twilightforest.entity.boss.LichEntity;

@Pseudo
@Mixin(targets = "twilightforest.entity.boss.LichMinionEntity", remap = false)
public interface TwilightLichMinionAccess {
    @Accessor(value = "master", remap = false) LichEntity soulcatcherplus$getMaster();
    @Accessor(value = "master", remap = false) void soulcatcherplus$setMaster(LichEntity master);
}
