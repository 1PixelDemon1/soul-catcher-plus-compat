// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa", remap = false)
public abstract class MowzieElokosaTerrainMixin {
    @Shadow(remap = false) public Vec3 jumpVel;

    @Inject(method = "findAndComputeLeap(Ljava/util/function/Supplier;)Z", at = @At(value = "INVOKE",
        target = "Lcom/bobmowzie/mowziesmobs/server/entity/effects/EntityBlockSwapper;swapBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;IZZ)V"),
        cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$skipLeapMarker(CallbackInfoReturnable<Boolean> callback) {
        // Both marker branches end by returning this same leap result.
        if (CompanionTerrain.isProtectedSoul((Entity)(Object)this)) callback.setReturnValue(jumpVel != null);
    }
}
