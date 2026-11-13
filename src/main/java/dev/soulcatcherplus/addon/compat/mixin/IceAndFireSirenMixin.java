// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.companion.CompanionBrain;
import com.github.alexthe666.iceandfire.entity.EntitySiren;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Native attack animations show the monster form; idle/chasing souls keep the calm appearance. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.EntitySiren", remap = false)
public abstract class IceAndFireSirenMixin {
    @Inject(method = "isAgressive()Z", at = @At("RETURN"), cancellable = true, remap = false)
    private void soulcatcherplus$gentleAppearance(CallbackInfoReturnable<Boolean> callback) {
        EntitySiren siren = (EntitySiren)(Object)this;
        if (siren.level.isClientSide && CompanionBrain.isSoul(siren)) {
            var animation = siren.getAnimation();
            callback.setReturnValue(animation == EntitySiren.ANIMATION_BITE || animation == EntitySiren.ANIMATION_PULL);
        }
    }
}
