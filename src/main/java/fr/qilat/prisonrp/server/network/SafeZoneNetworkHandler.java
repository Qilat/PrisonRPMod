package fr.qilat.prisonrp.server.network;

import fr.qilat.prisonrp.client.gui.GuiSfz;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.network.packets.SafeZonePacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class SafeZoneNetworkHandler {

    private static ArrayList<SafeZone> zones = new ArrayList<SafeZone>();

    public static ArrayList<SafeZone> getZones() {
        return zones;
    }

    public static void setZones(ArrayList<SafeZone> zones) {
        SafeZoneNetworkHandler.zones = zones;
    }

    public static void loadZones() {
        PacketHandler.sendPacketToServer(new SafeZonePacket(Minecraft.getMinecraft().player.getUniqueID(), SafeZonePacket.From.CLIENT, "", true));
    }

    public static void zonesLoaded(boolean openGUI) {
        if (openGUI)
            Minecraft.getMinecraft().displayGuiScreen(new GuiSfz());
    }

    public static SafeZone[] getNextSafeZones(int amount, int firstID) {
        SafeZone[] toReturn = new SafeZone[amount];

        for (int i = 0; i < amount; i++) {
            if (zones.size() >= firstID + i)
                toReturn[i] = zones.get(firstID + i);
        }

        return toReturn;
    }
}
