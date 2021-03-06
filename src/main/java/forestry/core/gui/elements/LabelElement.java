package forestry.core.gui.elements;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.Style;

import com.mojang.blaze3d.matrix.MatrixStack;

import forestry.core.gui.elements.lib.IGuiElement;
import forestry.core.gui.elements.lib.ILabelElement;
import forestry.core.gui.elements.lib.ITextElement;
import forestry.core.gui.tooltips.ITextInstance;

public class LabelElement extends GuiElement implements ILabelElement {
	/* Constants */
	public static final FontRenderer FONT_RENDERER = Minecraft.getInstance().fontRenderer;
	public static final int DEFAULT_HEIGHT = FONT_RENDERER.FONT_HEIGHT + 3;

	/* Attributes - State */
	protected ITextProperties component;
	protected int originalWidth;
	protected boolean fitText;
	protected boolean shadow = false;

	public LabelElement(ITextProperties component) {
		this(0, 0, -1, DEFAULT_HEIGHT, component, true);
	}

	public LabelElement(int xPos, int yPos, int width, int height, ITextProperties component, boolean fitText) {
		super(xPos, yPos, width, height);
		this.originalWidth = width;
		this.component = component;
		setFitText(fitText);
	}

	@Override
	public LabelElement setFitText(boolean fitText) {
		this.fitText = fitText;
		calculateWidth();
		return this;
	}

	@Override
	public boolean isFitText() {
		return fitText;
	}

	@Override
	public LabelElement setShadow(boolean value) {
		shadow = value;
		return this;
	}

	@Override
	public boolean hasShadow() {
		return shadow;
	}

	@Override
	public Style getStyle() {
		if (component instanceof IFormattableTextComponent) {
			return ((IFormattableTextComponent) component).getStyle();
		}
		return Style.EMPTY;
	}

	@Override
	public ILabelElement setStyle(Style style) {
		if (component instanceof IFormattableTextComponent) {
			((IFormattableTextComponent) component).func_240703_c_(style);
		}
		return this;
	}

	@Override
	public ITextProperties getValue() {
		return component;
	}

	@Override
	public boolean setValue(ITextProperties value) {
		this.component = value;
		calculateWidth();
		return true;
	}

	protected void calculateWidth() {
		if (fitText) {
			setWidth(FONT_RENDERER.getStringWidth(component.getString()));
		} else {
			setWidth(originalWidth);
		}
	}

	@Override
	public Collection<ITextProperties> getLines() {
		return Collections.singletonList(component);
	}

	@Override
	public ITextElement setText(ITextComponent text) {
		setValue((IFormattableTextComponent) text);
		return this;
	}

	@Override
	public void drawElement(MatrixStack transform, int mouseY, int mouseX) {
		if (shadow) {
			FONT_RENDERER.func_238407_a_(transform, component, 0, 0, 0);
		} else {
			FONT_RENDERER.func_238422_b_(transform, component, 0, 0, 0);
		}
	}

	public static class Builder implements ITextInstance<Builder, Builder, LabelElement> {
		private final Consumer<IGuiElement> parentAdder;
		private final IFormattableTextComponent root;
		private boolean fitText = false;
		private boolean shadow = false;

		public Builder(Consumer<IGuiElement> parentAdder, IFormattableTextComponent root) {
			this.parentAdder = parentAdder;
			this.root = root;
		}

		@Nullable
		@Override
		public ITextComponent lastComponent() {
			return root;
		}

		@Override
		public Builder add(ITextComponent line) {
			root.func_230529_a_(line);
			return this;
		}

		@Override
		public Builder singleLine() {
			return this;
		}

		@Override
		public Builder cast() {
			return this;
		}

		@Override
		public LabelElement create() {
			Preconditions.checkNotNull(root);
			LabelElement element = new LabelElement(root).setFitText(fitText).setShadow(shadow);
			parentAdder.accept(element);
			return element;
		}

		public Builder fitText() {
			this.fitText = true;
			return this;
		}

		public Builder shadow() {
			shadow = true;
			return this;
		}

		@Override
		public boolean isEmpty() {
			return root == null;
		}
	}
}
