// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.TwilightForestCompat;
import java.util.List;
import java.util.stream.Collectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.entity.boss.Lich;

@Pseudo
@Mixin(targets = "twilightforest.entity.ai.goal.LichShadowsGoal", remap = false)
public abstract class TwilightLichShadowsMixin {
    @Shadow(remap = false) @Final private Lich lich;

    @Redirect(method = "findNewMaster()V", at = @At(value = "INVOKE",
        target = "Ltwilightforest/entity/boss/Lich;getNearbyLiches()Ljava/util/List;"), remap = false)
    private List<Lich> soulcatcherplus$masters(Lich source) {
        return source.getNearbyLiches().stream().filter(candidate -> TwilightForestCompat.allowsMaster(lich, candidate))
            .collect(Collectors.toList());
    }

    @Redirect(method = "despawnClones()V", at = @At(value = "INVOKE",
        target = "Ltwilightforest/entity/boss/Lich;getNearbyLiches()Ljava/util/List;"), remap = false)
    private List<Lich> soulcatcherplus$ownClones(Lich source) {
        return source.getNearbyLiches().stream().filter(candidate -> TwilightForestCompat.mayDismissClone(lich, candidate))
            .collect(Collectors.toList());
    }
}
