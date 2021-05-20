package com.mrh0.createaddition.compat.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrh0.createaddition.index.CABlocks;
import com.mrh0.createaddition.recipe.crude_burning.CrudeBurningRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

public class CrudeBurningCategory extends CARecipeCategory<CrudeBurningRecipe> {

	public CrudeBurningCategory() {
		super(itemIcon(CABlocks.CRUDE_BURNER.get()), emptyBackground(177, 53));
	}

	@Override
	public Class<? extends CrudeBurningRecipe> getRecipeClass() {
		return CrudeBurningRecipe.class;
	}

	@Override
	public void setIngredients(CrudeBurningRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(new ArrayList<Ingredient>());
		ingredients.setOutputs(VanillaTypes.ITEM, new ArrayList<ItemStack>());
		ingredients.setInputLists(VanillaTypes.FLUID, NonNullList.from(recipe.getFluidIngredient().getMatchingFluidStacks()));
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrudeBurningRecipe recipe, IIngredients ingredients) {
		IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
		
		NonNullList<FluidIngredient> fluidIngredients = NonNullList.from(recipe.getFluidIngredient());
		
		List<FluidStack> out = new ArrayList<FluidStack>();
		
		fluidStacks.init(0, true, 81, 7);
		fluidStacks.set(0, withImprovedVisibility(recipe.getFluidIngredient().getMatchingFluidStacks()
				.stream()
				.map(fluid -> {
					out.add(fluid);
					return fluid;
				})
				.collect(Collectors.toList())));

		addFluidTooltip(fluidStacks, fluidIngredients, out);
	}

	@Override
	public void draw(CrudeBurningRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		//AllGuiTextures.JEI_SLOT.draw(matrixStack, 14, 8);
		//AllGuiTextures.JEI_ARROW.draw(matrixStack, 85, 32);
		//AllGuiTextures.JEI_DOWN_ARROW.draw(matrixStack, 43, 4);

		AllGuiTextures.JEI_SLOT.draw(matrixStack, 80, 6);

		Minecraft.getInstance().fontRenderer.draw(matrixStack, new TranslationTextComponent("createaddition.recipe.crude_burning.burn_time").getStringTruncated(Integer.MAX_VALUE) + ": " + ((double)recipe.getBurnTime()/20d)+"s", 9, 34, 4210752);
	}
}