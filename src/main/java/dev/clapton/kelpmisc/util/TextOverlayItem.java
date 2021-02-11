package dev.clapton.kelpmisc.util;

import jdk.internal.jline.internal.Nullable;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class TextOverlayItem {
    private final ITag.INamedTag<Item> itemTag;
    private final ITextOverlayFunc drawFunc;

    public TextOverlayItem(@Nullable ITag.INamedTag<Item> tag, ITextOverlayFunc func) {
        itemTag = tag;
        drawFunc = func;
    }

    public void draw(ClientPlayerEntity player, RenderGameOverlayEvent.Text event) {
        if(itemTag == null || player.inventory.hasTag(itemTag) || player.isCreative()) {
            drawFunc.draw(event);
        }
    }
}
