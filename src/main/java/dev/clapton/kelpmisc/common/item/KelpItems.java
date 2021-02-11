package dev.clapton.kelpmisc.common.item;

import dev.clapton.kelpmisc.util.KelpResource;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class KelpItems {
    public static Item BLAZE_PIPE;
    public static Item BONE_FRAGMENT;


    public static void registerAll(RegistryEvent.Register<Item> event) {
        if (!event.getName().equals(ForgeRegistries.ITEMS.getRegistryName())) {
            return;
        }

        BLAZE_PIPE = register("blaze_pipe", new PipeItem());
        BONE_FRAGMENT = register("bone_fragment", new Item(
                new Item.Properties()
                        .maxStackSize(64)
                        .group(KelpItemsGroup.getInstance())
                        .rarity(Rarity.COMMON)
        ));
    }

    public static <T extends Item> T register(String name, T item) {
        item.setRegistryName(KelpResource.prefix(name));
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
