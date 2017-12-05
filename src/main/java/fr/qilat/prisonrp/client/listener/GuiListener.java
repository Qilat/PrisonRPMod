package fr.qilat.prisonrp.client.listener;

import fr.qilat.prisonrp.client.gui.GuiCustomInGameMenu;
import fr.qilat.prisonrp.client.gui.GuiCustomInventory;
import fr.qilat.prisonrp.client.gui.GuiCustomMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Qilat on 14/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class GuiListener {

    public static boolean authorizeMultiGui = true; //TODO true if staff

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if (event.getGui() != null) {
            if (event.getGui().getClass() == GuiMainMenu.class
                    || (event.getGui().getClass() == GuiMultiplayer.class && !authorizeMultiGui)) {
                event.setGui(new GuiCustomMainMenu());
            } else if (event.getGui().getClass() == GuiIngameMenu.class) {
                event.setGui(new GuiCustomInGameMenu());
            } else if (event.getGui().getClass() == GuiInventory.class) {
                event.setGui(new GuiCustomInventory(Minecraft.getMinecraft().player));
            }
        }
    }


}
