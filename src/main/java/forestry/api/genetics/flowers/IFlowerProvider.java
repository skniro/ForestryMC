/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.genetics.flowers;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import genetics.api.individual.IIndividual;

import forestry.api.genetics.ICheckPollinatable;

public interface IFlowerProvider {

	boolean isAcceptedPollinatable(World world, ICheckPollinatable pollinatable);

	/**
	 * @return The unique type used for the IFlowerRegistry
	 * @since Forestry 4.0.0
	 */
	String getFlowerType();

	/**
	 * @return Short, human-readable identifier used in the beealyzer.
	 */
	ITextComponent getDescription();

	/**
	 * Allows the flower provider to affect the produce at the given location.
	 * If this flowerProvider does not affect the products, it should return the products unchanged.
	 *
	 * @return Array of itemstacks being the (modified or unmodified) produce.
	 */
	NonNullList<ItemStack> affectProducts(World world, IIndividual individual, BlockPos pos, NonNullList<ItemStack> products);
}
