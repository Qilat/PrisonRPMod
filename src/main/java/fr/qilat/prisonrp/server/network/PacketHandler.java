package fr.qilat.prisonrp.server.network;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.server.network.packets.SafeZonePacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class PacketHandler extends SimpleNetworkWrapper {
    private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(PrisonRPCore.MODID);
    private static int id = 0;

    private PacketHandler(String channelName) {
        super(channelName);
    }

    public static void registerPackets() {
        PacketHandler.INSTANCE.registerMessage(SafeZonePacket.SafeZonePacketHandler.class, SafeZonePacket.class, id++, Side.SERVER);
        PacketHandler.INSTANCE.registerMessage(SafeZonePacket.SafeZonePacketHandler.class, SafeZonePacket.class, id++, Side.CLIENT);

    }

    public static void sendPacket(IMessage message) {
        INSTANCE.sendToServer(message);
    }
}
