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
package forestry.core.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

import com.mojang.blaze3d.matrix.MatrixStack;

import forestry.core.render.ColourProperties;

public abstract class GuiForestryTitled<C extends Container> extends GuiForestry<C> {

	protected GuiForestryTitled(String texture, C container, PlayerInventory inv, ITextComponent title) {
		super(texture, container, inv, title);
	}

	@Override
	protected void func_230450_a_(MatrixStack transform, float partialTicks, int mouseX, int mouseY) {
		super.func_230450_a_(transform, partialTicks, mouseX, mouseY);

		textLayout.line = 6;
		if (centeredTitle()) {
			textLayout.drawCenteredLine(title.getString(), 0, ColourProperties.INSTANCE.get("gui.title"));
		} else {
			textLayout.drawLine(title.getString(), 8, ColourProperties.INSTANCE.get("gui.title"));
		}
		bindTexture(textureFile);
	}

	protected boolean centeredTitle() {
		return true;
	}
}
