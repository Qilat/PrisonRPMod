package fr.qilat.prisonrp.client.gui;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.component.GuiSafeZoneEntry;
import fr.qilat.prisonrp.server.network.SafeZoneNetworkHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

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
        Keyboard.enableRepeatEvents(true);

        if (!SafeZoneNetworkHandler.getZones().isEmpty()) {
            firstIDShown = SafeZoneNetworkHandler.getZones().get(0).getId();
        }

        insideLeft = this.width / 2 - GuiSfz.entryWidth / 2 + 2;
        insideTop = this.height / 2 - 256 / 2;

        //int entryHeight = GuiSfz.entryHeight + 10;
        int topPadding = 256 * 7 / 200;
        int yPos = insideTop + topPadding + 2;
        this.firstEntry = new GuiSafeZoneEntry(this, insideLeft, yPos, entryWidth, entryHeight);
        yPos += GuiSfz.entryHeight + topPadding;
        this.secondEntry = new GuiSafeZoneEntry(this, insideLeft, yPos, entryWidth, entryHeight);
        yPos += GuiSfz.entryHeight + topPadding;
        this.thirdEntry = new GuiSafeZoneEntry(this, insideLeft, yPos, entryWidth, entryHeight);
        yPos += GuiSfz.entryHeight + topPadding;
        this.fourthEntry = new GuiSafeZoneEntry(this, insideLeft, yPos, entryWidth, entryHeight);

        this.firstEntry.initGui();
        this.secondEntry.initGui();
        this.thirdEntry.initGui();
        this.fourthEntry.initGui();

    }

    public <T extends GuiButton> T addButton(T button){
        return super.addButton(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if(GuiSfz.firstIDShown != -1) {
            this.firstEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown));
            this.secondEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown + 1));
            this.thirdEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown + 2));
            this.fourthEntry.setSafeZone(SafeZoneNetworkHandler.getZones().get(GuiSfz.firstIDShown + 3));
        }

        this.drawEntries();
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        this.firstEntry.textboxKeyTyped(typedChar, keyCode);
        this.secondEntry.textboxKeyTyped(typedChar, keyCode);
        this.thirdEntry.textboxKeyTyped(typedChar, keyCode);
        this.fourthEntry.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 15) {
            if(this.firstEntry.isFocused()) this.firstEntry.nextFocus();
            if(this.secondEntry.isFocused()) this.secondEntry.nextFocus();
            if(this.thirdEntry.isFocused()) this.thirdEntry.nextFocus();
            if(this.fourthEntry.isFocused()) this.fourthEntry.nextFocus();
        }

        if (keyCode == 28 || keyCode == 156)
        {
            //TODO SAVE
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.firstEntry.mouseClicked(mouseX, mouseY, mouseButton);
        this.secondEntry.mouseClicked(mouseX, mouseY, mouseButton);
        this.thirdEntry.mouseClicked(mouseX, mouseY, mouseButton);
        this.fourthEntry.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id){
            case 7 :
                //TODO SAVE
                break;
            case 8:
                //TODO DEL
                break;
        }

    }

    private void drawEntries() {
        if (this.firstEntry != null)
            this.firstEntry.drawEntry();
        if (this.secondEntry != null)
            this.secondEntry.drawEntry();
        if (this.thirdEntry != null)
            this.thirdEntry.drawEntry();
        if (this.fourthEntry != null)
            this.fourthEntry.drawEntry();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateCursorPos();
    }

    private void updateCursorPos() {
        if (this.firstEntry != null)
            this.firstEntry.updateCursorPos();
        if (this.secondEntry != null)
            this.secondEntry.updateCursorPos();
        if (this.thirdEntry != null)
            this.thirdEntry.updateCursorPos();
        if (this.fourthEntry != null)
            this.fourthEntry.updateCursorPos();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        //TODO update value
    }

    @Override
    public void drawDefaultBackground() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(DEFAULT_BACKGROUND);
        Gui.drawScaledCustomSizeModalRect(this.width / 2 - 256 / 2, this.height / 2 - 256 / 2, 0, 0, 1, 1, 256, 256, 1, 1);
    }

}