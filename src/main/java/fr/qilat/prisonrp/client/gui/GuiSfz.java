package fr.qilat.prisonrp.client.gui;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.component.GuiSafeZoneEntry;
import fr.qilat.prisonrp.server.network.SafeZoneNetworkHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Qilat on 08/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSfz extends GuiScreen {
    private static final String ROOT_DIRECTORY = "textures/gui/safezone/";
    private static ResourceLocation DEFAULT_BACKGROUND;
    private static int firstIDShown = -1;
    private static int tempID;
    private static int insideLeft;
    private static int insideTop;
    private static int topPadding = 256 * 10 / 100;
    private static int entryWidth = 220;
    private static int entryHeight = 50;

    static {
        DEFAULT_BACKGROUND = new ResourceLocation(PrisonRPCore.MODID, ROOT_DIRECTORY + "defaultbackground.png");
    }

    private GuiSafeZoneEntry firstEntry;
    private GuiSafeZoneEntry secondEntry;
    private GuiSafeZoneEntry thirdEntry;
    private GuiSafeZoneEntry fourthEntry;

    @Override
    public void initGui() {
        super.initGui();
        if (!SafeZoneNetworkHandler.getZones().isEmpty()) {
            firstIDShown = SafeZoneNetworkHandler.getZones().get(0).getId();
        }

        insideLeft = this.width / 2 - GuiSfz.entryWidth / 2 + 2;
        insideTop = this.height / 2 - 256 / 2 + 4;

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();

        this.firstEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown));
        this.secondEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown + 1));
        this.thirdEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown + 2));
        this.fourthEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown + 3));

        this.drawEntries();

    }

    private void drawEntries() {

        int yPos = insideTop + topPadding;
        this.firstEntry.drawEntry(insideLeft, yPos, entryWidth, entryHeight);

        yPos = insideTop + GuiSfz.entryHeight + topPadding;
        this.secondEntry.drawEntry(insideLeft, yPos, entryWidth, entryHeight);

        yPos = insideTop + 2 * GuiSfz.entryHeight + topPadding;
        this.thirdEntry.drawEntry(insideLeft, yPos, entryWidth, entryHeight);

        yPos = insideTop + 3 * GuiSfz.entryHeight + topPadding;
        this.fourthEntry.drawEntry(insideLeft, yPos, entryWidth, entryHeight);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateCursorPos();

    }

    private void updateCursorPos(){
        this.firstEntry.updateCursorPos();
        this.secondEntry.updateCursorPos();
        this.thirdEntry.updateCursorPos();
        this.fourthEntry.updateCursorPos();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        //update value
    }

    @Override
    public void drawDefaultBackground() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(DEFAULT_BACKGROUND);
        Gui.drawScaledCustomSizeModalRect(this.width / 2 - 256 / 2, this.height / 2 - 256 / 2, 0, 0, 1, 1, 256, 256, 1, 1);
    }
    
}