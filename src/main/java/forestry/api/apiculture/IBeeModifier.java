/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.apiculture;

import javax.annotation.Nullable;

import genetics.api.individual.IGenome;

public interface IBeeModifier {
	/**
	 * @param genome          Genome of the bee this modifier is called for.
	 * @param currentModifier Current modifier.
	 * @return Float used to modify the base territory.
	 */
	float getTerritoryModifier(IGenome genome, float currentModifier);

	/**
	 * @param genome          Genome of the bee this modifier is called for.
	 * @param mate            Genome of the bee mate this modifier is called for.
	 * @param currentModifier Current modifier.
	 * @return Float used to modify the base mutation chance.
	 */
	float getMutationModifier(IGenome genome, IGenome mate, float currentModifier);

	/**
	 * @param genome          Genome of the bee this modifier is called for.
	 * @param currentModifier Current modifier.
	 * @return Float used to modify the life span of queens.
	 */
	float getLifespanModifier(IGenome genome, @Nullable IGenome mate, float currentModifier);

	/**
	 * @param genome          Genome of the bee this modifier is called for.
	 * @param currentModifier Current modifier.
	 * @return Float modifying the production speed of queens.
	 */
	float getProductionModifier(IGenome genome, float currentModifier);

	/**
	 * @param genome Genome of the bee this modifier is called for.
	 * @return Float modifying the flowering of queens.
	 */
	float getFloweringModifier(IGenome genome, float currentModifier);

	/**
	 * @param genome Genome of the bee this modifier is called for.
	 * @return Float modifying the chance for a swarmer queen to die off.
	 */
	float getGeneticDecay(IGenome genome, float currentModifier);

	/**
	 * @return Boolean indicating if housing can ignore rain
	 */
	boolean isSealed();

	/**
	 * @return Boolean indicating if housing can ignore darkness/night
	 */
	boolean isSelfLighted();

	/**
	 * @return Boolean indicating if housing can ignore not seeing the sky
	 */
	boolean isSunlightSimulated();

	/**
	 * @return Boolean indicating whether this housing simulates the nether
	 */
	boolean isHellish();

}
