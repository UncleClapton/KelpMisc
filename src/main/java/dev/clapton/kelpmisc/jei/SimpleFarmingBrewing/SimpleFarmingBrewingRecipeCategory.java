package dev.clapton.kelpmisc.jei.SimpleFarmingBrewing;



import dev.clapton.kelpmisc.util.Constants;
import enemeez.simplefarming.init.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import enemeez.simplefarming.item.crafting.BrewingBarrelRecipe;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleFarmingBrewingRecipeCategory implements IRecipeCategory<BrewingBarrelRecipe> {

    public static final ResourceLocation UID = new ResourceLocation("simplefarming", "brewing");
    private final Ingredient bottleIngredient = Ingredient.fromStacks(new ItemStack(Items.GLASS_BOTTLE));
    private final IDrawable background;
    private final IDrawable icon;
    private final String localizedName;

    public SimpleFarmingBrewingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(Constants.JEI_BARREL_BREWING_GUI, 0, 0, 82, 44);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.brewing_barrel));
        localizedName = I18n.format("block.simplefarming.brewing_barrel");
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }


    @Nonnull
    @Override
    public Class<? extends BrewingBarrelRecipe> getRecipeClass() {
        return BrewingBarrelRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(BrewingBarrelRecipe brewingBarrelRecipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(Stream.of(brewingBarrelRecipe.getIngredient(), bottleIngredient).collect(Collectors.toList()));
        //noinspection ConstantConditions
        iIngredients.setOutput(VanillaTypes.ITEM, brewingBarrelRecipe.getCraftingResult(null));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, @Nonnull BrewingBarrelRecipe brewingBarrelRecipe, @Nonnull IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStacks = iRecipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 13);
        guiItemStacks.init(1, true, 20, 0);
        guiItemStacks.init(2, false, 60, 13);

        guiItemStacks.set(iIngredients);
    }
}
