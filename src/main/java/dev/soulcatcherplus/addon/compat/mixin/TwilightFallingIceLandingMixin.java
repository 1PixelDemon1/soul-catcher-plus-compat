// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Vanilla removes the falling entity before trying to place its block. */
@Mixin(FallingBlockEntity.class)
public abstract class TwilightFallingIceLandingMixin {

    @Inject(method = {"tick()V", "func_70071_h_()V"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;setBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
        cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$placeFallingBlockOfficial(CallbackInfo callback) {
        if (CompanionTerrain.protectsTerrain((FallingBlockEntity)(Object)this)) callback.cancel();
    }

    @Inject(method = {"tick()V", "func_70071_h_()V"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;func_180501_a(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"),
        cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$placeFallingBlockSrg(CallbackInfo callback) {
        if (CompanionTerrain.protectsTerrain((FallingBlockEntity)(Object)this)) callback.cancel();
    }
}
