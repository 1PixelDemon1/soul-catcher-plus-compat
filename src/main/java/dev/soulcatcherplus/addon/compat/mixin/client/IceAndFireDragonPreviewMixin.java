// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin.client;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import dev.soulcatcherplus.api.client.SoulPreviews;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Detached dragons use a stable standing pose, without native tick/interpolation state. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.client.model.animator.DragonTabulaModelAnimator", remap = false)
public abstract class IceAndFireDragonPreviewMixin {
    @Shadow(remap = false)
    protected abstract void genderMob(EntityDragonBase dragon, AdvancedModelBox cube);

    @Inject(method = "setRotationAngles(Lcom/github/alexthe666/citadel/client/model/TabulaModel;Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;FFFFFF)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void soulcatcherplus$stablePreview(TabulaModel model, EntityDragonBase dragon, float limbSwing,
            float limbSwingAmount, float age, float headYaw, float headPitch, float scale, CallbackInfo callback) {
        if (!SoulPreviews.isPreview(dragon)) return;
        model.resetToDefaultPose();
        for (AdvancedModelBox cube : model.getCubes().values()) genderMob(dragon, cube);
        callback.cancel();
    }
}
