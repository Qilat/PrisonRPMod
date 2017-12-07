package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.GuiSafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.network.SafeZoneNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiScrollSafeZone extends GuiListExtended {


    private final GuiSafeZone owner;
    private final List<GuiSafeZoneEntry> safeZoneList = new ArrayList<GuiSafeZoneEntry>();
    private int selectedSlotIndex = -1;

    public GuiScrollSafeZone(GuiSafeZone ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.owner = ownerIn;
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    public GuiListExtended.IGuiListEntry getListEntry(int index) {
        if (index < this.safeZoneList.size()) {
            return this.safeZoneList.get(index);
        } else {
            return null;
        }

    }

    protected int getSize() {
        return this.safeZoneList.size();
    }

    public void setSelectedSlotIndex(int selectedSlotIndexIn) {
        this.selectedSlotIndex = selectedSlotIndexIn;
    }

    /**
     * Returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.selectedSlotIndex;
    }



    public int getSelected() {
        return this.selectedSlotIndex;
    }

    public void updateSafeZones() {
        System.out.println("update Safe zone ");
        this.safeZoneList.clear();
        List<SafeZone> list = SafeZoneNetworkHandler.getZones();
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(i);
            this.safeZoneList.add(new GuiSafeZoneEntry(this.owner, list.get(i)));
        }
        System.out.println(list.size() + " registered.");

    }

    protected int getScrollBarX() {
        return super.getScrollBarX() + 30;
    }

    @Override
    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
    }

    @Override
    protected void drawContainerBackground(Tessellator tessellator) {
    }

    /**
     * Gets the width of the list
     */
    public int getListWidth() {
        return super.getListWidth() + 85;
    }
}