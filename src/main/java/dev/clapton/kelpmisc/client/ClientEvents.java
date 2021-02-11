package dev.clapton.kelpmisc.client;


import dev.clapton.kelpmisc.KelpMisc;
import dev.clapton.kelpmisc.client.worker.TextHud;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = KelpMisc.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    private static final TextHud textHUD = new TextHud();

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRender(RenderGameOverlayEvent.Text event) {
        if (event.isCanceled()) {
            return;
        }

        textHUD.draw(event);
    }
}
