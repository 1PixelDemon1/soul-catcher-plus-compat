// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import dev.soulcatcherplus.addon.compat.CompanionTerrain;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    "twilightforest.entity.boss.AlphaYetiEntity",
    "twilightforest.entity.boss.HydraEntity",
    "twilightforest.entity.boss.KnightPhantomEntity",
    "twilightforest.entity.boss.LichEntity",
    "twilightforest.entity.boss.MinoshroomEntity",
    "twilightforest.entity.boss.NagaEntity",
    "twilightforest.entity.boss.PlateauBossEntity",
    "twilightforest.entity.boss.SnowQueenEntity",
    "twilightforest.entity.boss.UrGhastEntity",
    "twilightforest.entity.RisingZombieEntity",
    "twilightforest.entity.TrollEntity",
    "twilightforest.entity.UnstableIceCoreEntity",
    "twilightforest.entity.ChainBlockEntity",
    "twilightforest.entity.boss.IceBombEntity",
    "twilightforest.entity.projectile.CicadaShotEntity",
    "twilightforest.entity.projectile.MoonwormShotEntity",
    "twilightforest.entity.projectile.NatureBoltEntity",
    "twilightforest.entity.boss.FallingIceEntity"
}, remap = false)
public abstract class TwilightTerrainMixin {
    @Inject(method = {"ripenBer", "transformBlock", "extinguishNearbyCandles",
        "doTerrainEffect", "affectBlocksInAABB", "destroyIceInAABB"},
        at = @At("HEAD"), cancellable = true, remap = false, require = 0)
    private void soulcatcherplus$skipTerrainEffect(CallbackInfo callback) {
        if (CompanionTerrain.protectsTerrain((Entity)(Object)this)) callback.cancel();
    }

    @Redirect(method = {"checkDespawn", "func_70623_bb", "aiStep", "func_70636_d", "makeBlockFall", "hitNearbyEntities", "onHit", "func_70227_a"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockAndUpdate(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$preventBlockUpdateOfficial(World world, BlockPos pos, BlockState state) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && world.setBlockAndUpdate(pos, state);
    }

    @Redirect(method = {"checkDespawn", "func_70623_bb", "aiStep", "func_70636_d", "makeBlockFall", "hitNearbyEntities", "onHit", "func_70227_a"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;func_175656_a(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"), remap = false, require = 0)
    private boolean soulcatcherplus$preventBlockUpdateSrg(World world, BlockPos pos, BlockState state) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && world.setBlockAndUpdate(pos, state);
    }

    // The attack still spawns even though its source block stays in place.
    @ModifyArg(method = "makeBlockFall", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;addFreshEntity(Lnet/minecraft/entity/Entity;)Z"),
        index = 0, remap = false, require = 0)
    private Entity soulcatcherplus$protectFallingIceOfficial(Entity ice) {
        return soulcatcherplus$protectFallingIce(ice);
    }

    @ModifyArg(method = "makeBlockFall", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/World;func_217376_c(Lnet/minecraft/entity/Entity;)Z"),
        index = 0, remap = false, require = 0)
    private Entity soulcatcherplus$protectFallingIceSrg(Entity ice) {
        return soulcatcherplus$protectFallingIce(ice);
    }

    @org.spongepowered.asm.mixin.Unique
    private Entity soulcatcherplus$protectFallingIce(Entity ice) {
        CompanionTerrain.protectAttack(ice, (Entity)(Object)this);
        if (CompanionTerrain.protectsTerrain(ice) && ice instanceof FallingBlockEntity falling) {
            // Vanilla's first tick treats the projectile as a block pulled out of the world.
            falling.time = 1;
            falling.dropItem = false;
        }
        return ice;
    }

    @Redirect(method = {"onHit", "func_70227_a"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/item/BoneMealItem;growCrop(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"),
        remap = false, require = 0)
    private boolean soulcatcherplus$preventPlantGrowthOfficial(ItemStack stack, World world, BlockPos pos) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && BoneMealItem.growCrop(stack, world, pos);
    }

    @Redirect(method = {"onHit", "func_70227_a"}, at = @At(value = "INVOKE",
        target = "Lnet/minecraft/item/BoneMealItem;func_195966_a(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z"),
        remap = false, require = 0)
    private boolean soulcatcherplus$preventPlantGrowthSrg(ItemStack stack, World world, BlockPos pos) {
        return !CompanionTerrain.protectsTerrain((Entity)(Object)this) && BoneMealItem.growCrop(stack, world, pos);
    }
}
