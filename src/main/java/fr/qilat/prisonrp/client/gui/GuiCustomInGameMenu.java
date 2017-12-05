package fr.qilat.prisonrp.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

/**
 * Created by Qilat on 16/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class GuiCustomInGameMenu extends GuiScreen {
    public static final int RETURN_TO_GAME = 0;
    public static final int OPTIONS = 1;
    public static final int DISCONNECT = 2;
    private int saveStep;
    private int visibleTime;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.saveStep = 0;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(RETURN_TO_GAME, this.width / 2 - 100, this.height / 4 + 48 + -16, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(OPTIONS, this.width / 2 - 100, this.height / 4 + 72 + -16, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(DISCONNECT, this.width / 2 - 100, this.height / 4 + 96 + -16, I18n.format(this.mc.isIntegratedServerRunning() ? "menu.returnToMenu" : "menu.disconnect")));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case RETURN_TO_GAME:
                this.mc.displayGuiScreen((GuiScreen) null);
                this.mc.setIngameFocus();
                break;
            case OPTIONS:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case DISCONNECT:
                button.enabled = false;
                this.mc.world.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);

                this.mc.displayGuiScreen(new GuiCustomMainMenu());
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        super.updateScreen();
        ++this.visibleTime;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game"), this.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}