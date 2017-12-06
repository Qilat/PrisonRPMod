package fr.qilat.prisonrp.client.gui;

import fr.qilat.prisonrp.PrisonRPCore;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSafeZone extends GuiScreen {
    public static final String ROOT_DIRECTORY = "textures/gui/safezone/";
    public static final ResourceLocation DEFAULT_BACKGROUND = new ResourceLocation(PrisonRPCore.MODID, ROOT_DIRECTORY + "defaultbackground.png");

    public GuiSafeZone() {
        super();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

    }

    @Override
    public void initGui() {
        super.initGui();
    }
}
