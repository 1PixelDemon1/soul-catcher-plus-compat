// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Twilight Forest's direct block writes that do not consult Forge mob griefing. */
@Pseudo
@Mixin(targets = {
    "twilightforest.entity.boss.AlphaYeti",
    "twilightforest.entity.boss.Hydra",
    "twilightforest.entity.boss.KnightPhantom",
    "twilightforest.entity.boss.Lich",
    "twilightforest.entity.boss.Minoshroom",
    "twilightforest.entity.boss.Naga",
    "twilightforest.entity.boss.PlateauBoss",
    "twilightforest.entity.boss.SnowQueen",
    "twilightforest.entity.boss.UrGhast",
    "twilightforest.entity.monster.RisingZombie",
    "twilightforest.entity.monster.Troll",
    "twilightforest.entity.monster.UnstableIceCore",
    "twilightforest.entity.ChainBlock",
    "twilightforest.entity.projectile.IceBomb",
    "twilightforest.entity.projectile.MoonwormShot",
    "twilightforest.entity.projectile.NatureBolt"
}, remap = false)
public abstract class TwilightTerrainMixin {
    @Inject(method = {"ripenBer", "transformBlock", "extinguishNearbyCandles",
        "doTerrainEffect", "affectBlocksInAABB"},
        at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$skipTerrainEffect(CallbackInfo callback) {
        if (CompanionTerrain.protectsTerrain((Entity)(Object)this)) callback.cancel();
    }

    @Redirect(method = {"checkDespawn", "m_6043_", "aiStep", "m_8107_", "makeBlockFall", "hitNearbyEntities", "onHitBlock", "m_8060_"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$preventBlockUpdateOfficial(Level world, BlockPos pos, BlockState state) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && world.setBlockAndUpdate(pos, state);
    }

    @Redirect(method = {"checkDespawn", "m_6043_", "aiStep", "m_8107_", "makeBlockFall", "hitNearbyEntities", "onHitBlock", "m_8060_"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;m_46597_(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$preventBlockUpdateSrg(Level world, BlockPos pos, BlockState state) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && world.setBlockAndUpdate(pos, state);
    }

    @Redirect(method = {"tick", "m_8119_"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$preventRockRemovalOfficial(Level world, BlockPos pos, boolean moving) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && world.removeBlock(pos, moving);
    }

    @Redirect(method = {"tick", "m_8119_"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;m_7471_(Lnet/minecraft/core/BlockPos;Z)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$preventRockRemovalSrg(Level world, BlockPos pos, boolean moving) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && world.removeBlock(pos, moving);
    }

    // The attack still spawns even though its source block stays in place.
    @ModifyArg(method = "makeBlockFall", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"),
        index = 0, remap = false, require = 0)
    private Entity soulcatcherplus$protectFallingIceOfficial(Entity ice) {
        return soulcatcherplus$protectFallingIce(ice);
    }

    @ModifyArg(method = "makeBlockFall", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/level/Level;m_7967_(Lnet/minecraft/world/entity/Entity;)Z"),
        index = 0, remap = false, require = 0)
    private Entity soulcatcherplus$protectFallingIceSrg(Entity ice) {
        return soulcatcherplus$protectFallingIce(ice);
    }

    @org.spongepowered.asm.mixin.Unique
    private Entity soulcatcherplus$protectFallingIce(Entity ice) {
        CompanionTerrain.protectAttack(ice, (Entity)(Object)this);
        // Wild ice starts in the vacated ceiling block. A soul's ice starts below it.
        if (CompanionTerrain.protectsTerrain(ice)) ice.setPos(ice.getX(), ice.getY() - 1, ice.getZ());
        return ice;
    }

    @Redirect(method = "makeRandomBlockFall", at = @At(value = "INVOKE",
        target = "Lnet/minecraftforge/event/ForgeEventFactory;getMobGriefingEvent(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;)Z"),
        remap = false, require = 0)
    private boolean soulcatcherplus$allowFallingIceAttack(Level level, Entity yeti) {
        return CompanionTerrain.protectsTerrain(yeti) || ForgeEventFactory.getMobGriefingEvent(level, yeti);
    }
}
