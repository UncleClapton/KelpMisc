package dev.clapton.kelpmisc.common.item;

import dev.clapton.kelpmisc.KelpMisc;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;

public class KelpItemsGroup extends ItemGroup {
    private static KelpItemsGroup instance;
    public static KelpItemsGroup getInstance() {
        if (instance == null) {
            instance = new KelpItemsGroup();
        }

        return instance;
    }

    public KelpItemsGroup() {
        super(KelpMisc.MOD_ID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.DRIED_KELP);
    }
}
