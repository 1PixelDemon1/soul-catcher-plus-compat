// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.IceAndFireCompat;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Sea serpent leaf destruction bypasses both Forge and Ice and Fire grief events. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.EntitySeaSerpent", remap = false)
public abstract class IceAndFireSeaSerpentMixin {
    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true, remap = false)
    private void soulcatcherplus$preserveBlocks(CallbackInfo callback) {
        if (IceAndFireCompat.isSoul((Entity)(Object)this)) callback.cancel();
    }
}
