package forestry.core.gui.buttons;

import net.minecraft.client.gui.widget.button.Button;

import com.mojang.blaze3d.matrix.MatrixStack;

import forestry.core.gui.Drawable;

public class GuiToggleButton extends Button {
	/* attributes - Final */
	private final Drawable[] textures = new Drawable[3];

	public GuiToggleButton(int x, int y, int widthIn, int heightIn, Drawable drawable, IPressable handler) {
		super(x, y, widthIn, heightIn, null, handler);
		for (int i = 0; i < 3; i++) {
			textures[i] = new Drawable(drawable.textureLocation, drawable.u, drawable.v + drawable.vHeight * i, drawable.uWidth, drawable.vHeight);
		}
	}

	@Override
	public void render(MatrixStack transform, int mouseX, int mouseY, float partialTicks) {
		this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		//		int hoverState = this.getHoverState(this.isHovered);
		Drawable drawable = textures[0];    //TODO think I need isHovered and wasHovered
		drawable.draw(transform, y, x);
	}
}
