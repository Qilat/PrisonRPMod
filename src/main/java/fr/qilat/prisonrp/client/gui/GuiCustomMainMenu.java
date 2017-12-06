package fr.qilat.prisonrp.client.gui;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.component.GuiButtonDev;
import fr.qilat.prisonrp.client.gui.component.GuiButtonDiscord;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GLContext;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Qilat on 14/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class GuiCustomMainMenu extends GuiScreen {
    public static final String ROOT_DIRECTORY = "textures/gui/mainmenu/";
    private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation(PrisonRPCore.MODID, ROOT_DIRECTORY + "title.png");
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(PrisonRPCore.MODID,ROOT_DIRECTORY + "backgroundmainmenu.png");
    private static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
    private static final int PLAY_BTT = 100;
    private static final int OPTIONS_BTT = 0;
    private static final int QUIT_BTT = 4;
    private static final int LANG_BTT = 5;
    private static final int DISCORD_BTT = 101;
    private static final int DEV_BTT = 102;
    /**
     * Counts the number of screen updates.
     */
    private final float updateCounter;
    private final boolean mcoEnabled = true;
    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();
    /**
     * The splash message.
     */
    private String splashText;
    private GuiButton buttonResetDemo;
    /**
     * Timer used to rotate the panorama, increases every tick.
     */
    private int panoramaTimer;
    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning1;
    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning2;
    /**
     * Link to the Mojang Support about minimum requirements
     */
    private String openGLWarningLink;
    /**
     * Width of openGLWarning2
     */
    private int openGLWarning2Width;
    /**
     * Width of openGLWarning1
     */
    private int openGLWarning1Width;
    /**
     * Left x coordinate of the OpenGL warning
     */
    private int openGLWarningX1;
    /**
     * Top y coordinate of the OpenGL warning
     */
    private int openGLWarningY1;
    /**
     * Right x coordinate of the OpenGL warning
     */
    private int openGLWarningX2;
    /**
     * Bottom y coordinate of the OpenGL warning
     */
    private int openGLWarningY2;
    private ResourceLocation backgroundTexture;

    public GuiCustomMainMenu() {
        this.openGLWarning2 = MORE_INFO_TEXT;
        this.splashText = "missingno";

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1");
            this.openGLWarning2 = I18n.format("title.oldgl2");
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }

        FMLClientHandler.instance().setupServerList();
    }


    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int interval = 24;
        int j = this.height / 4 + 48;

        this.buttonList.add(new GuiButton(PLAY_BTT, this.width / 2 - 100, j, I18n.format("menu.play")));
        this.buttonList.add(new GuiButton(OPTIONS_BTT, this.width / 2 - 100, j + interval * 1, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(QUIT_BTT, this.width / 2 - 100, j + interval * 2, I18n.format("menu.quit")));
        this.buttonList.add(new GuiButtonLanguage(LANG_BTT, this.width / 2 + (20 * -3), j + interval * 3));
        this.buttonList.add(new GuiButtonDiscord(DISCORD_BTT, this.width / 2 - (20 / 2), j + interval * 3));
        this.buttonList.add(new GuiButtonDev(DEV_BTT, this.width / 2 + (20 * 2), j + interval * 3));


        synchronized (this.threadLock) {
            this.openGLWarning1Width = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.openGLWarning2Width = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
            this.openGLWarningX1 = (this.width - k) / 2;
            this.openGLWarningY1 = this.buttonList.get(0).yPosition - 24;
            this.openGLWarningX2 = this.openGLWarningX1 + k;
            this.openGLWarningY2 = this.openGLWarningY1 + 24;
        }
        this.mc.setConnectedToRealms(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == PLAY_BTT) {
            FMLClientHandler.instance().connectToServer(this, new ServerData("PrisonRP", "play.prisonrp.fr", false));
            //this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }

        if (button.id == OPTIONS_BTT) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == QUIT_BTT) {
            this.mc.shutdown();
        }

        if (button.id == LANG_BTT) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == DISCORD_BTT) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/qxFPSS6"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        if (button.id == DEV_BTT) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

    }

    public void confirmClicked(boolean result, int id) {
        if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
                    oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new URI(this.openGLWarningLink));
                } catch (Throwable throwable) {
                    LOGGER.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        mc.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);

        GlStateManager.enableAlpha();
        this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.drawTexturedModalRect(this.width / 2 - 256 / 2, 25, 0, 0, 256, 64);
        GlStateManager.disableBlend();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) (this.width / 2), 70.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);

        float f = 1.8F - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
        f = f * 100.0F / (float) (this.fontRendererObj.getStringWidth(this.splashText) + 32);

        GlStateManager.scale(0.5 * f, 0.5 * f, 0.5 * f);
        GlStateManager.popMatrix();

        java.util.List<String> brandings = new ArrayList<String>();
        brandings.add("Copyright Mojang AB. Do not distribute!");
        brandings.add("Minecraft 1.10.2 release PrisonRP");

        for (int brdline = 0; brdline < brandings.size(); brdline++) {
            String brd = brandings.get(brdline);
            if (!com.google.common.base.Strings.isNullOrEmpty(brd)) {
                this.drawString(this.fontRendererObj, brd, 2, this.height - (10 + brdline * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
            }
        }

        if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
            drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, this.buttonList.get(0).yPosition - 12, -1);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock) {
            if (!this.openGLWarning1.isEmpty() && mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
        net.minecraftforge.client.ForgeHooksClient.mainMenuMouseClick(mouseX, mouseY, mouseButton, this.fontRendererObj, this.width);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed() {
    }
}
