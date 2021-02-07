package dev.clapton.kelpmisc.jei;


import dev.clapton.kelpmisc.KelpMisc;
import dev.clapton.kelpmisc.jei.SimpleFarmingBrewing.SimpleFarmingBrewingRecipeCategory;
import enemeez.simplefarming.init.ModBlocks;
import enemeez.simplefarming.init.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


@JeiPlugin
public class KelpJEI implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(KelpMisc.MOD_ID);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new SimpleFarmingBrewingRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        World world = Minecraft.getInstance().world;
        if (world == null) {
            return;
        }
        RecipeManager recipeManager = world.getRecipeManager();

        registration.addRecipes(recipeManager.getRecipesForType(ModRecipes.BREWING_BARREL_RECIPE_TYPE), SimpleFarmingBrewingRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.brewing_barrel), SimpleFarmingBrewingRecipeCategory.UID);
    }
}
