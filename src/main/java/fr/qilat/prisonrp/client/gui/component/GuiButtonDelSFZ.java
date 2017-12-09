package fr.qilat.prisonrp.client.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * Created by Qilat on 09/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class GuiButtonDelSFZ extends GuiButton {

    private final ResourceLocation iconTexture = new ResourceLocation("textures/gui/container/beacon.png");
    private final int iconX;
    private final int iconY;
    private boolean selected;

    protected GuiButtonDelSFZ(int buttonId, int x, int y) {
        super(buttonId, x, y, 22, 22, "");
        this.iconX = 112;
        this.iconY = 220;
        this.visible = true;
        this.enabled = true;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(iconTexture);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = 219;
            int j = 0;

            if (!this.enabled) {
                j += this.width * 2;
            } else if (this.selected) {
                j += this.width * 1;
            } else if (this.hovered) {
                j += this.width * 3;
            }
            //draw background
            this.drawTexturedModalRect(this.xPosition, this.yPosition, j, 219, this.width, this.height);
            //draw Icon
            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.iconX, this.iconY, 18, 18);
        }
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selectedIn) {
        this.selected = selectedIn;
    }
}
