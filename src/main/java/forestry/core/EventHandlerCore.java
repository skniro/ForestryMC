/*******************************************************************************
 * Copyright (c) 2011-2014 SirSengir.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Various Contributors including, but not limited to:
 * SirSengir (original work), CovertJaguar, Player, Binnie, MysteriousAges
 ******************************************************************************/
package forestry.core;

import java.net.URL;
import java.util.Collection;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import genetics.api.GeneticsAPI;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

import forestry.api.core.ISpriteRegistry;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IForestrySpeciesRoot;
import forestry.apiculture.ApiaristAI;
import forestry.apiculture.ModuleApiculture;
import forestry.core.config.Constants;
import forestry.core.models.ModelBlockCached;
import forestry.core.render.TextureManagerForestry;
import forestry.modules.ModuleManager;

public class EventHandlerCore {

	public EventHandlerCore() {
	}

	//TODO - register event handler
	@SubscribeEvent
	public void handleItemPickup(EntityItemPickupEvent event) {
		if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) {
			return;
		}

		for (IPickupHandler handler : ModuleManager.pickupHandlers) {
			if (handler.onItemPickup(event.getPlayer(), event.getItem())) {
				event.setResult(Event.Result.ALLOW);
				return;
			}
		}
	}

	@SubscribeEvent
	public void handlePlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		PlayerEntity player = event.getPlayer();
		syncBreedingTrackers(player);
	}

	@SubscribeEvent
	public void handlePlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		PlayerEntity player = event.getPlayer();
		syncBreedingTrackers(player);
	}

	private static void syncBreedingTrackers(PlayerEntity player) {
		Collection<IRootDefinition> speciesRoots = GeneticsAPI.apiInstance.getRoots().values();
		for (IRootDefinition definition : speciesRoots) {
			if (!definition.isPresent()) {
				continue;
			}
			IIndividualRoot root = definition.get();
			if (!(root instanceof IForestrySpeciesRoot)) {
				continue;
			}
			IForestrySpeciesRoot speciesRoot = (IForestrySpeciesRoot) root;
			IBreedingTracker breedingTracker = speciesRoot.getBreedingTracker(player.getEntityWorld(), player.getGameProfile());
			breedingTracker.synchToPlayer(player);
		}
	}

	@SubscribeEvent
	public void handleWorldLoad(WorldEvent.Load event) {
		IWorld world = event.getWorld();

		for (ISaveEventHandler handler : ModuleManager.saveEventHandlers) {
			handler.onWorldLoad(world);
		}
	}

	@SubscribeEvent
	public void handleWorldSave(WorldEvent.Save event) {
		for (ISaveEventHandler handler : ModuleManager.saveEventHandlers) {
			handler.onWorldSave(event.getWorld());
		}
	}

	@SubscribeEvent
	public void handleWorldUnload(WorldEvent.Unload event) {
		for (ISaveEventHandler handler : ModuleManager.saveEventHandlers) {
			handler.onWorldUnload(event.getWorld());
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void handleTextureRemap(TextureStitchEvent.Pre event) {
		if (event.getMap().getTextureLocation() == PlayerContainer.LOCATION_BLOCKS_TEXTURE) {
			TextureManagerForestry.getInstance().registerSprites(ISpriteRegistry.fromEvent(event));
			ModelBlockCached.clear();
		}
	}

	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent event) {
		if (!event.getName().getNamespace().equals("minecraft")
			&& !event.getName().equals(Constants.VILLAGE_NATURALIST_LOOT_KEY)) {
			return;
		}

		Set<String> lootPoolNames = ModuleManager.getLootPoolNames();

		for (String lootTableFile : ModuleManager.getLootTableFiles()) {
			ResourceLocation resourceLocation = new ResourceLocation(Constants.MOD_ID, event.getName().getPath() + "/" + lootTableFile);
			URL url = EventHandlerCore.class.getResource("/assets/" + resourceLocation.getNamespace() + "/loot_tables/" + resourceLocation.getPath() + ".json");
			if (url != null) {
				LootTable forestryChestAdditions = event.getLootTableManager().getLootTableFromLocation(resourceLocation);
				if (forestryChestAdditions != LootTable.EMPTY_LOOT_TABLE) {
					for (String poolName : lootPoolNames) {
						LootPool pool = forestryChestAdditions.getPool(poolName);
						if (pool != null) {
							event.getTable().addPool(pool);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void handleVillagerAI(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		if ((entity instanceof VillagerEntity)) {
			VillagerEntity villager = (VillagerEntity) entity;
			//TODO - not sure this is quite right
			VillagerProfession prof = ForgeRegistries.PROFESSIONS.getValue(EntityType.getKey(villager.getType()));
			if (ModuleApiculture.villagerApiarist != null && prof == ModuleApiculture.villagerApiarist) {
				villager.goalSelector.addGoal(6, new ApiaristAI(villager, 0.6));
			}
		}
	}
}
