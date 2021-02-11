package dev.clapton.kelpmisc.util;

import dev.clapton.kelpmisc.KelpMisc;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class KelpTags {

    public static final INamedTag<Item> GUI__POSITION = itemTag(KelpMisc.MOD_ID, "gui/position");
    public static final INamedTag<Item> GUI__CLOCK = itemTag(KelpMisc.MOD_ID, "gui/clock");

    public static final INamedTag<Item> PIPE_FUEL = itemTag(KelpMisc.MOD_ID, "pipe_fuel");


    public static INamedTag<Item> itemTag(String modID, String name) {
        return ItemTags.makeWrapperTag(new ResourceLocation(modID, name).toString());
    }
}
