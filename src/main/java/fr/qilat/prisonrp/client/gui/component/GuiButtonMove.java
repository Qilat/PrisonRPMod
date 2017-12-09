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
public abstract class GuiButtonMove extends GuiButton {

    private static final ResourceLocation iconTexture = new ResourceLocation("textures/gui/resource_packs.png");
    int iconX;
    int iconY;
    private boolean selected;

    protected GuiButtonMove(int buttonId, int x, int y, int width, int height) {
        super(buttonId, x, y, width, height, "");
        this.visible = true;
        this.enabled = true;
    }

    /**
     * Draws this button to the screen.
     */
    public abstract void drawButton(Minecraft mc, int mouseX, int mouseY);

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selectedIn) {
        this.selected = selectedIn;
    }


    public static class Up extends GuiButtonMove {
        public Up(int buttonId, int x, int y, int width, int height) {
            super(buttonId, x, y, width, height);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {
                mc.getTextureManager().bindTexture(iconTexture);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

                if (this.hovered) {
                    this.iconX = 112;
                    this.iconY = 36;
                } else {
                    this.iconX = 112;
                    this.iconY = 4;
                }

                this.drawTexturedModalRect(this.xPosition, this.yPosition, this.iconX, this.iconY, this.width, this.height);
            }
        }
    }

    public static class Down extends GuiButtonMove {
        public Down(int buttonId, int x, int y, int width, int height) {
            super(buttonId, x, y, width, height);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {
                mc.getTextureManager().bindTexture(iconTexture);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

                if (this.hovered) {
                    this.iconX = 81;
                    this.iconY = 51;
                } else {
                    this.iconX = 81;
                    this.iconY = 19;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, this.iconX, this.iconY, this.width, this.height);

            }
        }
    }
}