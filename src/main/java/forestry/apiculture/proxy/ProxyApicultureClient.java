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
package forestry.apiculture.proxy;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.MinecartRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import forestry.apiculture.features.ApicultureBlocks;
import forestry.apiculture.features.ApicultureEntities;
import forestry.core.entities.ParticleSnow;
import forestry.modules.IClientModuleHandler;

@OnlyIn(Dist.CLIENT)
public class ProxyApicultureClient extends ProxyApiculture implements IClientModuleHandler {

	@Override
	public void setupClient(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ApicultureEntities.APIARY_MINECART.entityType(), MinecartRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ApicultureEntities.BEE_HOUSE_MINECART.entityType(), MinecartRenderer::new);
		ApicultureBlocks.BEE_COMB.getBlocks().forEach((block) -> RenderTypeLookup.setRenderLayer(block, RenderType.getCutout()));
	}

	@Override
	public void registerSprites(TextureStitchEvent.Pre event) {
		//TODO textures
		for (int i = 0; i < ParticleSnow.sprites.length; i++) {
			//			ParticleSnow.sprites[i] = event.getMap().registerSprite(new ResourceLocation("forestry:entity/particles/snow." + (i + 1)));
		}
		//		beeSprite = event.getMap().registerSprite(new ResourceLocation("forestry:entity/particles/swarm_bee"));
	}
}
