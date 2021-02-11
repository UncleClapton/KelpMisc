package dev.clapton.kelpmisc.client.worker;


import dev.clapton.kelpmisc.util.MinecraftTime;
import dev.clapton.kelpmisc.util.KelpTags;
import dev.clapton.kelpmisc.util.TextOverlayItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class TextHud {
    private static final String txtSep = TextFormatting.WHITE + ", ";

    private final Minecraft minecraft = Minecraft.getInstance();

    private final TextOverlayItem[] componentList = new TextOverlayItem[]{
            new TextOverlayItem(KelpTags.GUI__POSITION, (RenderGameOverlayEvent.Text event) -> {
                ClientPlayerEntity player = minecraft.player;
                if (player == null) {
                    return;
                }

                String posString = String.format(
                        "%s%.2f%s%s%.2f%s%s%.2f %s(%s)",
                        TextFormatting.RED, player.getPosX(),
                        txtSep,
                        TextFormatting.GREEN, player.getPosY(),
                        txtSep,
                        TextFormatting.AQUA, player.getPosZ(),
                        TextFormatting.GRAY, player.getHorizontalFacing()
                );

                event.getLeft().add(posString);
            }),

            new TextOverlayItem(KelpTags.GUI__CLOCK, (RenderGameOverlayEvent.Text event) -> {
                World world = minecraft.world;
                if (world == null) {
                    return;
                }

                event.getLeft().add(
                        TextFormatting.YELLOW + MinecraftTime.getDayTimeString(world.getDayTime())
                );
            }),
    };

    public void draw(RenderGameOverlayEvent.Text event) {
        if (minecraft.player == null
                || minecraft.world == null
                || minecraft.gameSettings.showDebugInfo
        ) {
            return;
        }

        for (TextOverlayItem comp : componentList) {
            comp.draw(minecraft.player, event);
        }
    }
}
