package fr.qilat.prisonrp.client.gui;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.component.GuiSafeZoneEntry;
import fr.qilat.prisonrp.client.gui.component.GuiScrollSafeZone;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSafeZone extends GuiContainer {
    public static final String ROOT_DIRECTORY = "textures/gui/safezone/";
    public static ResourceLocation DEFAULT_BACKGROUND;

    static {
        DEFAULT_BACKGROUND = new ResourceLocation(PrisonRPCore.MODID, ROOT_DIRECTORY + "defaultbackground.png");
    }

    public List<GuiSafeZoneEntry> list = new ArrayList<GuiSafeZoneEntry>();
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
        this.guiScrollSafeZone = new GuiScrollSafeZone(this, this.mc, this.width, this.height, this.height / 2 - 256 / 2, this.height / 2 + 256 / 2, 50);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.guiScrollSafeZone.updateSafeZones();
        this.guiScrollSafeZone.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        //update value
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.guiScrollSafeZone.updateSafeZones();
        for (GuiSafeZoneEntry guiSafeZoneEntry : this.guiScrollSafeZone.getSafeZoneList())
            guiSafeZoneEntry.updateCursorPos();
    }


}
