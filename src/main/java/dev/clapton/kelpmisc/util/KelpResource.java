package dev.clapton.kelpmisc.util;

import dev.clapton.kelpmisc.KelpMisc;
import net.minecraft.util.ResourceLocation;

public class KelpResource {
    public static final ResourceLocation JEI_BARREL_BREWING_GUI = KelpResource.prefix("textures/gui/gui_barrel_brewing.png");

    public static ResourceLocation prefix(String pathIn) {
        return new ResourceLocation(KelpMisc.MOD_ID, pathIn);
    }
}
