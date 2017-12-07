package fr.qilat.prisonrp.server.network;

import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.network.packets.SafeZonePacket;

import java.util.ArrayList;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class SafeZoneNetworkHandler {

    private static ArrayList<SafeZone> zones = new ArrayList<SafeZone>();

    public static ArrayList<SafeZone> getZones() {
        PacketHandler.sendPacket(new SafeZonePacket(SafeZonePacket.From.CLIENT));
        return zones;
    }

    public static void setZones(ArrayList<SafeZone> zones) {
        SafeZoneNetworkHandler.zones = zones;
    }
}
