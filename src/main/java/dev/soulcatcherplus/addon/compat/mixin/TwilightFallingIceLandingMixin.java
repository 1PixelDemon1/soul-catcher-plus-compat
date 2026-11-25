// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "twilightforest.entity.projectile.FallingIce", remap = false)
public abstract class TwilightFallingIceLandingMixin {

    @Inject(method = {"tick()V", "m_8119_()V"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"),
        cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$finishFallingIceOfficial(CallbackInfo callback) {
        soulcatcherplus$finishFallingIce(callback);
    }

    @Inject(method = {"tick()V", "m_8119_()V"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;m_7731_(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"),
        cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$finishFallingIceSrg(CallbackInfo callback) {
        soulcatcherplus$finishFallingIce(callback);
    }

    @Unique
    private void soulcatcherplus$finishFallingIce(CallbackInfo callback) {
        Entity ice = (Entity)(Object)this;
        // Native landing discards only after successful placement.
        if (CompanionTerrain.protectsTerrain(ice)) {
            ice.discard();
            callback.cancel();
        }
    }
}
