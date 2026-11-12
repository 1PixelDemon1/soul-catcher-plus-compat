// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import dev.soulcatcherplus.addon.compat.IceAndFireCompat;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Suppress terrain mutations without changing charge radius or breath entity damage. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.IafDragonDestructionManager", remap = false)
public abstract class IceAndFireDragonDestructionMixin {
    @Inject(method = "attackBlock(Lnet/minecraft/world/level/Level;Lcom/github/alexthe666/iceandfire/entity/EntityDragonBase;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
        at = @At("HEAD"), cancellable = true, remap = false)
    private static void soulcatcherplus$breathTerrain(Level world, EntityDragonBase dragon, BlockPos pos,
            BlockState state, CallbackInfo callback) {
        if (IceAndFireCompat.isSoul(dragon)) callback.cancel();
    }

    @Inject(method = "destroyBlocks", at = @At("HEAD"), cancellable = true, remap = false)
    private static void soulcatcherplus$destroyTerrain(Level world, BlockPos center, int x, int y, int z,
            double radiusSquared, Entity destroyer, CallbackInfo callback) {
        if (IceAndFireCompat.isSoul(destroyer)) callback.cancel();
    }

    @Group(name = "soulcatcherplus$chargeTerrain", min = 2, max = 2)
    @Redirect(method = "destroyAreaCharge", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/core/BlockPos;betweenClosedStream(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Ljava/util/stream/Stream;"), remap = false, require = 0)
    private static Stream<BlockPos> soulcatcherplus$terrainOfficial(BlockPos from, BlockPos to,
            Level world, BlockPos center, EntityDragonBase dragon) {
        return soulcatcherplus$terrain(from, to, dragon);
    }

    @Group(name = "soulcatcherplus$chargeTerrain", min = 2, max = 2)
    @Redirect(method = "destroyAreaCharge", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/core/BlockPos;m_121990_(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Ljava/util/stream/Stream;"), remap = false, require = 0)
    private static Stream<BlockPos> soulcatcherplus$terrainSrg(BlockPos from, BlockPos to,
            Level world, BlockPos center, EntityDragonBase dragon) {
        return soulcatcherplus$terrain(from, to, dragon);
    }

    @org.spongepowered.asm.mixin.Unique
    private static Stream<BlockPos> soulcatcherplus$terrain(BlockPos from, BlockPos to, EntityDragonBase dragon) {
        return IceAndFireCompat.isSoul(dragon) ? Stream.empty() : BlockPos.betweenClosedStream(from, to);
    }
}
