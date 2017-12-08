package fr.qilat.prisonrp.client.gui.component;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.GuiSafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.network.SafeZoneNetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class GuiScrollSafeZone extends GuiSlot {
    private static final String ROOT_DIRECTORY = "textures/gui/safezone/";
    private static ResourceLocation DEFAULT_BACKGROUND;

    static {
        DEFAULT_BACKGROUND = new ResourceLocation(PrisonRPCore.MODID, ROOT_DIRECTORY + "defaultbackground.png");
    }

    private final GuiSafeZone owner;
    private final List<GuiSafeZoneEntry> safeZoneList = new ArrayList<GuiSafeZoneEntry>();
    private int selectedSlotIndex = -1;

    public GuiScrollSafeZone(GuiSafeZone ownerIn, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
        this.owner = ownerIn;
    }

    /*
    private GuiListExtended.IGuiListEntry getListEntry(int index) {
        if (index < this.safeZoneList.size()) {
            return this.safeZoneList.get(index);
        } else {
            return null;
        }

    }*/

    public List<GuiSafeZoneEntry> getSafeZoneList() {
        return safeZoneList;
    }

    public void updateSafeZones() {
        this.safeZoneList.clear();
        List<SafeZone> list = SafeZoneNetworkHandler.getZones();
        for (int i = 0; i < list.size(); ++i) {
            //this.safeZoneList.add(new GuiSafeZoneEntry(this.owner, list.get(i)));
        }
    }

    public int getSize() {
        return this.safeZoneList.size();
    }

    public int getSelected() {
        return this.selectedSlotIndex;
    }

    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.selectedSlotIndex;
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int entryID, int insideLeft, int yPos, int slotHeight, int mouseXIn, int mouseYIn) {
        //  this.getListEntry(entryID).drawEntry(entryID, insideLeft, yPos, this.getListWidth(), slotHeight, mouseXIn, mouseYIn, this.isMouseYWithinSlotBounds(mouseYIn) && this.getSlotIndexFromScreenCoords(mouseXIn, mouseYIn) == entryID);
    }

    @Override
    protected void drawContainerBackground(Tessellator tessellator) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(DEFAULT_BACKGROUND);
        Gui.drawScaledCustomSizeModalRect(this.width / 2 - 256 / 2, this.height / 2 - 256 / 2, 0, 0, 1, 1, 256, 256, 1, 1);
    }

    @Override
    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
        //super.overlayBackground(startY, endY, startAlpha, endAlpha);
    }

    @Override
    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
        if (this.visible) {
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            int i = this.getScrollBarX();
            int j = i + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();

            this.drawContainerBackground(tessellator);

            int k = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int l = this.top + 4 - (int) this.amountScrolled;

            if (this.hasListHeader) {
                this.drawListHeader(k, l, tessellator);
            }

            this.drawSelectionBox(k, l, mouseXIn, mouseYIn);

            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();

            this.renderDecorations(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    @Override
    protected void drawSelectionBox(int insideLeft, int insideTop, int mouseXIn, int mouseYIn) {
        int amountDraw = 4;
        int topPadding = 256 * 10 / 100;

        for (int id = 0; id < this.getSize() && id < amountDraw; ++id) {
            int yPos = insideTop + id * this.slotHeight + topPadding;
            int slotHeight = this.slotHeight - 4;

            this.drawSlot(id, insideLeft, yPos, slotHeight, mouseXIn, mouseYIn);
        }
    }

}