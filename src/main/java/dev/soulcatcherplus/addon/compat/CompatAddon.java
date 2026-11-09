// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import dev.soulcatcherplus.api.SoulAdapters;
import net.minecraft.resources.Identifier;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** Combined development addon; all third-party creature mods remain optional. */
@Mod(CompatAddon.ID)
public final class CompatAddon {
    public static final String ID = "soulcatcherplus_compat";

    public CompatAddon(FMLJavaModLoadingContext context) {
        FMLCommonSetupEvent.getBus(context.getModBusGroup()).addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (ModList.isLoaded("twilightforest")) TwilightForestCompat.register();
            org.apache.logging.log4j.LogManager.getLogger().info("Soul Catcher Plus integration addon initialized");
        });
    }
}
