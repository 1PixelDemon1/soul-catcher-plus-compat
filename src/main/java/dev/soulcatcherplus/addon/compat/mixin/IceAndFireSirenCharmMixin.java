// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Resolve restored charm ownership before suppressing its effects and next data sync. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.props.SirenData", remap = false)
public abstract class IceAndFireSirenCharmMixin {
    @Shadow(remap = false) public EntitySiren charmedBy;
    @Shadow(remap = false) public abstract void clearCharm();

    @Inject(method = "tickCharmed", at = @At(value = "INVOKE",
        target = "Lcom/github/alexthe666/iceandfire/entity/EntitySiren;isActuallySinging()Z"),
        cancellable = true, remap = false)
    private void soulcatcherplus$preventFriendlyCharm(LivingEntity holder, CallbackInfo callback) {
        if (charmedBy != null && CompanionBrain.isSoul(charmedBy) && CompanionBrain.allied(charmedBy, holder)) {
            clearCharm();
            callback.cancel();
        }
    }
}
