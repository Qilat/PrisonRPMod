package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.client.gui.GuiSfz;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSafeZoneEntry {

    private static int fontHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;

    private int baseX;
    private int baseY;
    private int listWidth;
    private int slotHeight;

    private int oldId = -1;
    private GuiSfz owner;
    private Minecraft mc;
    private SafeZone safeZone;

    private GuiTextField name;
    private GuiTextField pos1x;
    private GuiTextField pos1y;
    private GuiTextField pos1z;
    private GuiTextField pos2x;
    private GuiTextField pos2y;
    private GuiTextField pos2z;

    private GuiButtonSaveSFZ buttonSaveSFZ;
    private GuiButtonDelSFZ buttonDelSFZ;

    public GuiSafeZoneEntry(GuiSfz owner, int x, int y, int listWidth, int slotHeight) {
        this.owner = owner;
        this.mc = Minecraft.getMinecraft();
        this.baseX = x + 5;
        this.baseY = y + 6;
        this.listWidth = listWidth;
        this.slotHeight = slotHeight;

    }

    public void initGui() {
        int intervalY = 4;
        int intervalX = 4;
        int fieldHeight = 10;
        int fieldWidth = 40;
        int baseX = this.baseX + 2;
        int baseY = this.baseY + 5;

        this.name = new GuiTextField(6, this.mc.fontRendererObj, baseX + 48, baseY - 1, fieldWidth * 17/8 + 1, fieldHeight); // 1/8
        this.pos1x = new GuiTextField(0, this.mc.fontRendererObj, baseX + 0 * fieldWidth + 1 * intervalX, baseY + fontHeight + intervalY, fieldWidth, fieldHeight);
        this.pos1y = new GuiTextField(1, this.mc.fontRendererObj, baseX + 1 * fieldWidth + 2 * intervalX, baseY + fontHeight + intervalY, fieldWidth, fieldHeight);
        this.pos1z = new GuiTextField(2, this.mc.fontRendererObj, baseX + 2 * fieldWidth + 3 * intervalX, baseY + fontHeight + intervalY, fieldWidth + 2, fieldHeight);
        this.pos2x = new GuiTextField(3, this.mc.fontRendererObj, baseX + 0 * fieldWidth + 1 * intervalX, baseY + fontHeight + 2 * intervalY + fieldHeight, fieldWidth, fieldHeight);
        this.pos2y = new GuiTextField(4, this.mc.fontRendererObj, baseX + 1 * fieldWidth + 2 * intervalX, baseY + fontHeight + 2 * intervalY + fieldHeight, fieldWidth, fieldHeight);
        this.pos2z = new GuiTextField(5, this.mc.fontRendererObj, baseX + 2 * fieldWidth + 3 * intervalX, baseY + fontHeight + 2 * intervalY + fieldHeight, fieldWidth + 2, fieldHeight);

        if (getSafeZone() != null) {
            setTextFiledContent();
        }

        baseX += 2;
        baseY -= 2;
        this.buttonSaveSFZ = new GuiButtonSaveSFZ(7, baseX  + 3 * fieldWidth + 4 * intervalX, baseY );
        this.buttonDelSFZ = new GuiButtonDelSFZ(8, baseX + 3 * fieldWidth + 4 * intervalX, baseY + 23);

        this.owner.addButton(this.buttonSaveSFZ);
        this.owner.addButton(this.buttonDelSFZ);

    }

    private void setTextFiledContent() {
        this.name.setText(getSafeZone().getName() != null ? getSafeZone().getName() : "Undefined name");

        this.pos1x.setText(String.valueOf(getSafeZone().getPos1X()));
        this.pos1y.setText(String.valueOf(getSafeZone().getPos1Y()));
        this.pos1z.setText(String.valueOf(getSafeZone().getPos1Z()));

        this.pos2x.setText(String.valueOf(getSafeZone().getPos2X()));
        this.pos2y.setText(String.valueOf(getSafeZone().getPos2Y()));
        this.pos2z.setText(String.valueOf(getSafeZone().getPos2Z()));
    }

    private void drawTextField() {
        this.name.drawTextBox();

        this.pos1x.drawTextBox();
        this.pos1y.drawTextBox();
        this.pos1z.drawTextBox();

        this.pos2x.drawTextBox();
        this.pos2y.drawTextBox();
        this.pos2z.drawTextBox();
    }

    public void drawEntry() {
        if (this.safeZone != null) {
            int rectHeight = slotHeight;
            int rectWidth = listWidth * 75 / 100;

            Gui.drawRect(this.baseX, this.baseY + 2, this.baseX + rectWidth, this.baseY + rectHeight - 1, new Color(100, 100, 100).getRGB());
            this.mc.fontRendererObj.drawString("ID n°" + Integer.toString(this.safeZone.getId()) + " : Undefined name", baseX + 7, baseY + 5, new Color(255, 255, 255).getRGB());

            if (getSafeZone() != null
                    && this.oldId != getSafeZone().getId()) {
                this.setTextFiledContent();
                this.oldId = this.getSafeZone().getId();
            }

            this.drawTextField();
        }
    }

    public void updateCursorPos() {
        if(this.name != null)
            this.name.updateCursorCounter();

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

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.name.mouseClicked(mouseX, mouseY, mouseButton);
        this.pos1x.mouseClicked(mouseX, mouseY, mouseButton);
        this.pos1y.mouseClicked(mouseX, mouseY, mouseButton);
        this.pos1z.mouseClicked(mouseX, mouseY, mouseButton);
        this.pos2x.mouseClicked(mouseX, mouseY, mouseButton);
        this.pos2y.mouseClicked(mouseX, mouseY, mouseButton);
        this.pos2z.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void textboxKeyTyped(char typedChar, int keyCode) {
        this.name.textboxKeyTyped(typedChar, keyCode);
        this.pos1x.textboxKeyTyped(typedChar, keyCode);
        this.pos1y.textboxKeyTyped(typedChar, keyCode);
        this.pos1z.textboxKeyTyped(typedChar, keyCode);
        this.pos2x.textboxKeyTyped(typedChar, keyCode);
        this.pos2y.textboxKeyTyped(typedChar, keyCode);
        this.pos2z.textboxKeyTyped(typedChar, keyCode);
    }

    public boolean isFocused() {
        return this.name.isFocused()
                || this.pos1x.isFocused()
                || this.pos1y.isFocused()
                || this.pos1z.isFocused()
                || this.pos2x.isFocused()
                || this.pos2y.isFocused()
                || this.pos2z.isFocused();
    }

    public void nextFocus() {
        if(this.name.isFocused()){
            this.name.setFocused(false);
            this.pos1x.setFocused(true);
        }
        if(this.pos1x.isFocused()){
            this.pos1x.setFocused(false);
            this.pos1y.setFocused(true);
        }
        if(this.pos1y.isFocused()){
            this.pos1y.setFocused(false);
            this.pos1z.setFocused(true);
        }
        if(this.pos1z.isFocused()){
            this.pos1z.setFocused(false);
            this.pos2x.setFocused(true);
        }
        if(this.pos2x.isFocused()){
            this.pos2x.setFocused(false);
            this.pos2y.setFocused(true);
        }
        if(this.pos2y.isFocused()){
            this.pos2y.setFocused(false);
            this.pos2z.setFocused(true);
        }
        if(this.pos2z.isFocused()){
            this.pos2z.setFocused(false);
            this.name.setFocused(true);
        }
    }
}
