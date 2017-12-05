package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.PrisonRPCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Qilat on 18/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class GuiButtonDev extends GuiButton {

    private static final ResourceLocation DEV_ICON = new ResourceLocation(PrisonRPCore.MODID, "textures/gui/btt_gartox.png");

    private static final ResourceLocation DEV_HOVER_ICON = new ResourceLocation(PrisonRPCore.MODID, "textures/gui/btt_gartox_hover.png");

    public GuiButtonDev(int buttonId, int x, int y) {
        super(buttonId, x, y, 20, 20, "");
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            boolean mouseHover = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            if (mouseHover) {
                mc.getTextureManager().bindTexture(DEV_HOVER_ICON);
            } else {
                mc.getTextureManager().bindTexture(DEV_ICON);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawScaledCustomSizeModalRect(this.xPosition, this.yPosition, 0, 0, 300, 300, 20, 20, 300, 300);

        }

    }
}
