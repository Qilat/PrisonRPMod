package fr.qilat.prisonrp.client.gui;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.component.GuiScrollSafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSafeZone extends GuiContainer {
    public static final String ROOT_DIRECTORY = "textures/gui/safezone/";
    public static ResourceLocation DEFAULT_BACKGROUND;

    static{
        DEFAULT_BACKGROUND = new ResourceLocation(PrisonRPCore.MODID, ROOT_DIRECTORY + "defaultbackground.png");
    }

    public GuiScrollSafeZone guiScrollSafeZone;
    public GuiSafeZone() {
        super(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn) {
                return false;
            }
        });
    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiScrollSafeZone = new GuiScrollSafeZone(this, this.mc, this.width, this.height, 32, this.height - 64, 36);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.guiScrollSafeZone.updateSafeZones();
        this.guiScrollSafeZone.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(DEFAULT_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.guiScrollSafeZone.updateSafeZones();
    }


}
