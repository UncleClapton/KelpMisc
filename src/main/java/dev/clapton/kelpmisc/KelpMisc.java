package dev.clapton.kelpmisc;

import dev.clapton.kelpmisc.common.item.KelpItems;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(KelpMisc.MOD_ID)
public class KelpMisc
{
    public static final String MOD_ID = "kelpmisc";

    public KelpMisc() {

    }

    @Mod.EventBusSubscriber(modid = KelpMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            KelpItems.registerAll(event);
        }
    }
}
