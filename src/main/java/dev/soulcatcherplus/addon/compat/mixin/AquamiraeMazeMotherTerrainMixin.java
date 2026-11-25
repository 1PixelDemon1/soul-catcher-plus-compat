// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = {
    "com.obscuria.aquamirae.common.entities.MazeMother",
    "dev.obscuria.aquamirae.common.entity.MazeMother"
}, remap = false)
public abstract class AquamiraeMazeMotherTerrainMixin {
    @Inject(method = "breakIce(I)V", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$preventIceBreaking(CallbackInfo callback) {
        if (CompanionTerrain.isProtectedSoul((Entity)(Object)this)) callback.cancel();
    }
}
