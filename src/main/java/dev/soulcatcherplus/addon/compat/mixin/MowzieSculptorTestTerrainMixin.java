// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor", remap = false)
public abstract class MowzieSculptorTestTerrainMixin {
    // The trade handler starts the terrain-clearing test only after this payment succeeds.
    @Inject(method = "fulfillDesire(Lnet/minecraft/world/inventory/Slot;)Z", at = @At("HEAD"),
        cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$preventArenaClearing(CallbackInfoReturnable<Boolean> callback) {
        if (CompanionTerrain.isProtectedSoul((Entity)(Object)this)) callback.setReturnValue(false);
    }
}
