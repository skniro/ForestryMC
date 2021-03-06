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
package forestry.mail.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import com.mojang.blaze3d.matrix.MatrixStack;

import forestry.core.config.Constants;
import forestry.core.gui.GuiForestry;
import forestry.core.render.ColourProperties;
import forestry.core.utils.Translator;
import forestry.mail.tiles.TileTrader;

public class GuiTrader extends GuiForestry<ContainerTrader> {
	private final TileTrader tile;

	public GuiTrader(ContainerTrader container, PlayerInventory inv, ITextComponent title) {
		super(Constants.TEXTURE_PATH_GUI + "/mailtrader2.png", container, inv, title);
		this.tile = container.getTile();
		this.xSize = 226;
		this.ySize = 220;
	}

	@Override
	protected void func_230451_b_(MatrixStack transform, int mouseX, int mouseY) {
		String name = Translator.translateToLocal(tile.getUnlocalizedTitle());
		this.minecraft.fontRenderer.drawString(transform, name, textLayout.getCenteredOffset(name), 6, ColourProperties.INSTANCE.get("gui.mail.text"));

		String receive = Translator.translateToLocal("for.gui.mail.receive");
		this.minecraft.fontRenderer.drawString(transform, receive, textLayout.getCenteredOffset(receive, 70) + 51, 45, ColourProperties.INSTANCE.get("gui.mail.text"));

		String send = Translator.translateToLocal("for.gui.mail.send");
		this.minecraft.fontRenderer.drawString(transform, send, textLayout.getCenteredOffset(send, 70) + 51, 99, ColourProperties.INSTANCE.get("gui.mail.text"));

		super.func_230451_b_(transform, mouseX, mouseY);
	}

	@Override
	protected void func_230450_a_(MatrixStack transform, float partialTicks, int mouseY, int mouseX) {
		super.func_230450_a_(transform, partialTicks, mouseY, mouseX);

		this.minecraft.fontRenderer.drawString(transform, container.getAddress().getName(), guiLeft + 19, guiTop + 22, ColourProperties.INSTANCE.get("gui.mail.text"));
	}

	@Override
	protected void addLedgers() {
		addErrorLedger(tile);
		addHintLedger("trade.station");
		addOwnerLedger(tile);
	}
}
