// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import dev.soulcatcherplus.api.SoulAdapter;
import net.minecraft.nbt.CompoundNBT;

/** Hydra heads are separate renderer layers and need their own saved visual state. */
public final class IceAndFireHydraAdapter implements SoulAdapter {
    @Override public CompoundNBT previewData(CompoundNBT snapshot) {
        CompoundNBT preview = SoulAdapter.super.previewData(snapshot);
        preview.putInt("HeadCount", snapshot.contains("HeadCount") ? snapshot.getInt("HeadCount") : 3);
        preview.putInt("SeveredHead", snapshot.contains("SeveredHead") ? snapshot.getInt("SeveredHead") : -1);
        return preview;
    }
}
