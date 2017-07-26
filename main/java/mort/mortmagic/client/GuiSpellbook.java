package mort.mortmagic.client;

import mort.mortmagic.ClientProxy;
import mort.mortmagic.MortMagic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiSpellbook extends GuiContainer {

	public static final ResourceLocation background =
			new ResourceLocation("mortmagic","textures/spellbook.png");

	public GuiSpellbook(Container p_i1072_1_) {
		super(p_i1072_1_);
		this.xSize = 176;
		this.ySize = 113;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		int k = guiLeft;
        int l = guiTop;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == ((ClientProxy)MortMagic.proxy).keyBind.specInventory.getKeyCode() )
		{
			this.mc.thePlayer.closeScreen();
		} else
			super.keyTyped(par1, par2);
	}

}
