// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import dev.soulcatcherplus.companion.CompanionBrain;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Block friendly charm at its source and remove saved charm from that same source. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.props.SirenProperties", remap = false)
public abstract class IceAndFireSirenCharmMixin {
    @Shadow(remap = false) public static EntitySiren getSiren(LivingEntity entity) { throw new AssertionError(); }
    @Shadow(remap = false) private static void clearCharmedStatus(LivingEntity entity) { throw new AssertionError(); }

    @Unique
    private static boolean soulcatcherplus$friendly(LivingEntity victim, LivingEntity source) {
        return source instanceof EntitySiren siren && CompanionBrain.isSoul(siren)
            && CompanionBrain.allied(siren, victim);
    }

    @Inject(method = "setCharmedBy", at = @At("HEAD"), cancellable = true, remap = false)
    private static void soulcatcherplus$preventCharm(LivingEntity victim, LivingEntity source, CallbackInfo callback) {
        if (soulcatcherplus$friendly(victim, source)) callback.cancel();
    }

    @Inject(method = "tickCharmedEntity", at = @At("HEAD"), cancellable = true, remap = false)
    private static void soulcatcherplus$clearOldCharm(LivingEntity victim, CallbackInfo callback) {
        if (soulcatcherplus$friendly(victim, getSiren(victim))) {
            clearCharmedStatus(victim);
            callback.cancel();
        }
    }
}
