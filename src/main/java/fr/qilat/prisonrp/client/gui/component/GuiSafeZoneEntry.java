package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.client.gui.GuiSafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiSafeZoneEntry implements GuiListExtended.IGuiListEntry {
    private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
    private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
    private final GuiSafeZone owner;
    private final Minecraft mc;
    private final SafeZone safeZone;

    protected GuiSafeZoneEntry(GuiSafeZone guiSafeZone, SafeZone safeZone) {
        this.owner = guiSafeZone;
        this.safeZone = safeZone;
        this.mc = Minecraft.getMinecraft();
    }

    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {

        this.mc.fontRendererObj.drawString("Id" + Integer.toString(this.safeZone.getId()), x + 32 + 3, y + 1, 16777215);
        List<String> list = new ArrayList<String>();
        list.add("POS 1 : x =" + this.safeZone.getPos1X() + " z = " + this.safeZone.getPos1Z());
        list.add("POS 2 : x =" + this.safeZone.getPos2X() + " z = " + this.safeZone.getPos2Z());

        for (int i = 0; i < Math.min(list.size(), 2); ++i) {
            this.mc.fontRendererObj.drawString(list.get(i), x + 32 + 3, y + 12 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTextureAt(x, y, UNKNOWN_SERVER);

        if (this.mc.gameSettings.touchscreen || isSelected) {
            this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
            Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int k1 = mouseX - x;
            int l1 = mouseY - y;

            if (this.canJoin()) {
                if (k1 < 32 && k1 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
                } else {
                    Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
                }
            }
        }
    }

    protected void drawTextureAt(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
        this.mc.getTextureManager().bindTexture(p_178012_3_);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
        GlStateManager.disableBlend();
    }

    private boolean canJoin() {
        return true;
    }


    /**
     * Called when the mouse is clicked within this entry. Returning true means that something within this entry was
     * clicked and the list should not be dragged.
     */
    public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
        /*if (relativeX <= 32)
        {
            if (relativeX < 32 && relativeX > 16 && this.canJoin())
            {
                //this.owner.selectServer(slotIndex);
                //this.owner.connectToSelected();
                return true;
            }

            if (relativeX < 16 && relativeY < 16 && this.owner.canMoveUp(this, slotIndex))
            {
                this.owner.moveServerUp(this, slotIndex, GuiScreen.isShiftKeyDown());
                return true;
            }

            if (relativeX < 16 && relativeY > 16 && this.owner.canMoveDown(this, slotIndex))
            {
                this.owner.moveServerDown(this, slotIndex, GuiScreen.isShiftKeyDown());
                return true;
            }
        }

        this.owner.selectServer(slotIndex);

        if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
        {
            this.owner.connectToSelected();
        }

        this.lastClickTime = Minecraft.getSystemTime();*/
        return false;
    }

    public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {
    }

    /**
     * Fired when the mouse button is released. Arguments: index, x, y, mouseEvent, relativeX, relativeY
     */
    public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
    }

    public SafeZone getSafeZone() {
        return this.safeZone;
    }


}
