// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Only the client appearance changes; native server combat still sees aggression. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.EntitySiren", remap = false)
public abstract class IceAndFireSirenMixin {
    @Inject(method = "isAgressive()Z", at = @At("RETURN"), cancellable = true, remap = false)
    private void soulcatcherplus$gentleAppearance(CallbackInfoReturnable<Boolean> callback) {
        Entity entity = (Entity)(Object)this;
        if (entity.level.isClientSide && CompanionBrain.isSoul(entity)) callback.setReturnValue(false);
    }
}
