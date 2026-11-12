// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat.mixin;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import dev.soulcatcherplus.addon.compat.IceAndFireCompat;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Skip terrain loops while preserving native breath/charge entity damage and effects. */
@Pseudo
@Mixin(targets = "com.github.alexthe666.iceandfire.entity.IafDragonDestructionManager", remap = false)
public abstract class IceAndFireDragonDestructionMixin {
    @Shadow(remap = false)
    private static void destroyBlocks(World world, BlockPos center, int x, int y, int z, double radiusSquared) {
        throw new AssertionError();
    }

    @Group(name = "soulcatcherplus$dragonTerrain", min = 12, max = 12)
    @Redirect(method = {"destroyAreaFire", "destroyAreaIce", "destroyAreaLightning",
        "destroyAreaFireCharge", "destroyAreaIceCharge", "destroyAreaLightningCharge"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;betweenClosedStream(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/util/stream/Stream;"),
        remap = false, require = 0)
    private static Stream<BlockPos> soulcatcherplus$terrainOfficial(BlockPos from, BlockPos to,
            World world, BlockPos center, EntityDragonBase dragon) {
        return soulcatcherplus$terrain(from, to, dragon);
    }

    @Group(name = "soulcatcherplus$dragonTerrain", min = 12, max = 12)
    @Redirect(method = {"destroyAreaFire", "destroyAreaIce", "destroyAreaLightning",
        "destroyAreaFireCharge", "destroyAreaIceCharge", "destroyAreaLightningCharge"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;func_218281_b(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Ljava/util/stream/Stream;"),
        remap = false, require = 0)
    private static Stream<BlockPos> soulcatcherplus$terrainSrg(BlockPos from, BlockPos to,
            World world, BlockPos center, EntityDragonBase dragon) {
        return soulcatcherplus$terrain(from, to, dragon);
    }

    @org.spongepowered.asm.mixin.Unique
    private static Stream<BlockPos> soulcatcherplus$terrain(BlockPos from, BlockPos to, EntityDragonBase dragon) {
        return IceAndFireCompat.isSoul(dragon) ? Stream.empty() : BlockPos.betweenClosedStream(from, to);
    }

    @Redirect(method = {"destroyAreaFireCharge", "destroyAreaIceCharge", "destroyAreaLightningCharge"},
        at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/entity/IafDragonDestructionManager;destroyBlocks(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;IIID)V"),
        remap = false, require = 3)
    private static void soulcatcherplus$chargeTerrain(World world, BlockPos center, int x, int y, int z, double radiusSquared,
            World originalWorld, BlockPos originalCenter, EntityDragonBase dragon) {
        if (!IceAndFireCompat.isSoul(dragon)) destroyBlocks(world, center, x, y, z, radiusSquared);
    }
}
