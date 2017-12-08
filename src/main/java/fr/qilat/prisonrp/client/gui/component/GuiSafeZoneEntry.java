package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSafeZoneEntry {
    private static int fieldWidth = 40;
    private static int fieldHeight = 10;

    private static int intervalX = 4;
    private static int intervalY = 4;

    private static int fontHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;

    private int baseX;
    private int baseY;

    private int oldId = -1;
    private Minecraft mc;
    private SafeZone safeZone;

    private GuiTextField pos1x;
    private GuiTextField pos1y;
    private GuiTextField pos1z;
    private GuiTextField pos2x;
    private GuiTextField pos2y;
    private GuiTextField pos2z;

    GuiSafeZoneEntry(int x, int y) {
        this.mc = Minecraft.getMinecraft();
        this.baseX = x + 3;
        this.baseY = y + 3;

    }

    public void initGui() {
        this.pos1x = new GuiTextField(0, this.mc.fontRendererObj, baseX + 0 * fieldWidth + 1 * intervalX, baseY + fontHeight + intervalY, fieldWidth, fieldHeight);
        this.pos1y = new GuiTextField(1, this.mc.fontRendererObj, baseX + 1 * fieldWidth + 2 * intervalX, baseY + fontHeight + intervalY, fieldWidth, fieldHeight);
        this.pos1z = new GuiTextField(2, this.mc.fontRendererObj, baseX + 2 * fieldWidth + 3 * intervalX, baseY + fontHeight + intervalY, fieldWidth, fieldHeight);
        this.pos2x = new GuiTextField(3, this.mc.fontRendererObj, baseX + 0 * fieldWidth + 1 * intervalX, baseY + fontHeight + 2 * intervalY + fieldHeight, fieldWidth, fieldHeight);
        this.pos2y = new GuiTextField(4, this.mc.fontRendererObj, baseX + 1 * fieldWidth + 2 * intervalX, baseY + fontHeight + 2 * intervalY + fieldHeight, fieldWidth, fieldHeight);
        this.pos2z = new GuiTextField(5, this.mc.fontRendererObj, baseX + 2 * fieldWidth + 3 * intervalX, baseY + fontHeight + 2 * intervalY + fieldHeight, fieldWidth, fieldHeight);

        if (getSafeZone() != null) {
            setTextFiledContent();
        }
    }

    private void setTextFiledContent() {
        this.pos1x.setText(String.valueOf(getSafeZone().getPos1X()));
        this.pos1y.setText(String.valueOf(getSafeZone().getPos1Y()));
        this.pos1z.setText(String.valueOf(getSafeZone().getPos1Z()));

        this.pos2x.setText(String.valueOf(getSafeZone().getPos2X()));
        this.pos2y.setText(String.valueOf(getSafeZone().getPos2Y()));
        this.pos2z.setText(String.valueOf(getSafeZone().getPos2Z()));
    }

    private void drawTextField() {
        this.pos1x.drawTextBox();
        this.pos1y.drawTextBox();
        this.pos1z.drawTextBox();

        this.pos2x.drawTextBox();
        this.pos2y.drawTextBox();
        this.pos2z.drawTextBox();
    }

    public void drawEntry(int x, int y, int listWidth, int slotHeight) {
        if(this.safeZone != null) {
            int rectHeight = slotHeight;
            int rectWidth = listWidth * 75 / 100;

            Gui.drawRect(x, y + 1, x + rectWidth, y + rectHeight - 1, new Color(100, 100, 100).getRGB());
            this.mc.fontRendererObj.drawString("ID n°" + Integer.toString(this.safeZone.getId()) + " : Undefined name", baseX, baseY, new Color(255, 255, 255).getRGB());

            if (getSafeZone() != null
                    && this.oldId != getSafeZone().getId()) {
                this.setTextFiledContent();
                this.oldId = this.getSafeZone().getId();
            }

            this.drawTextField();
        }
    }

    public void updateCursorPos() {
        if (this.pos1x != null)
            this.pos1x.updateCursorCounter();
        if (this.pos1y != null)
            this.pos1y.updateCursorCounter();
        if (this.pos1z != null)
            this.pos1z.updateCursorCounter();

        if (this.pos2x != null)
            this.pos2x.updateCursorCounter();
        if (this.pos2y != null)
            this.pos2y.updateCursorCounter();
        if (this.pos2z != null)
            this.pos2z.updateCursorCounter();
    }

    private SafeZone getSafeZone() {
        return this.safeZone;
    }

    public void setSafeZone(SafeZone zone) {
        if (this.oldId != zone.getId())
            this.safeZone = zone;
    }
}
