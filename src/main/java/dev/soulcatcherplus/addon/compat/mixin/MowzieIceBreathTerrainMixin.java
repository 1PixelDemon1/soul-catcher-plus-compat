// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.MowzieSpellOwner;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBreath", remap = false)
public abstract class MowzieIceBreathTerrainMixin {
    @Inject(method = {"tick()V", "m_8119_()V"}, at = @At("HEAD"), remap = false, require = 0)
    private void soulcatcherplus$rememberSpellCaster(CallbackInfo callback) {
        Entity spell = (Entity)(Object)this;
        if (!spell.level.isClientSide) MowzieSpellOwner.protectsTerrain(spell);
    }

    @Inject(method = "freezeBlocks()V", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$protectTerrain(CallbackInfo callback) {
        if (MowzieSpellOwner.protectsTerrain((Entity)(Object)this)) callback.cancel();
    }
}
