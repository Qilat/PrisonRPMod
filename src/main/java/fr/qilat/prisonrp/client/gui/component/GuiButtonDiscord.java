package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.GuiCustomMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Qilat on 15/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class GuiButtonDiscord extends GuiButton {

    private static final ResourceLocation DISCORD_ICON = new ResourceLocation(PrisonRPCore.MODID, GuiCustomMainMenu.ROOT_DIRECTORY + "discord.png");

    private static final ResourceLocation DISCORD_HOVER_ICON = new ResourceLocation(PrisonRPCore.MODID, GuiCustomMainMenu.ROOT_DIRECTORY + "discord_hover.png");

    public GuiButtonDiscord(int buttonId, int x, int y) {
        super(buttonId, x, y, 20, 20, "");
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        if (this.visible) {
            boolean mouseHover = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            if (mouseHover) {
                mc.getTextureManager().bindTexture(DISCORD_HOVER_ICON);
            } else {
                mc.getTextureManager().bindTexture(DISCORD_ICON);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawScaledCustomSizeModalRect(this.xPosition, this.yPosition, 0, 0, 300, 300, 20, 20, 300, 300);

        }

    }
}
