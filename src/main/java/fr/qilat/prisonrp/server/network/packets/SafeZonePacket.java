package fr.qilat.prisonrp.server.network.packets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZoneManager;
import fr.qilat.prisonrp.server.network.PacketHandler;
import fr.qilat.prisonrp.server.network.SafeZoneNetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class SafeZonePacket implements IMessage {

    private String uuid;
    private From side;
    private String json = null;
    private boolean openGui = false;

    public SafeZonePacket() {
    }

    public SafeZonePacket(UUID uuid, From side, String json, boolean openGui) {
        this.uuid = uuid.toString();
        this.side = side;
        this.json = json;
        this.openGui = openGui;
    }

    public SafeZonePacket(From side) {
        this(null, side, "", false);
    }

    @SideOnly(Side.SERVER)
    public static void sendSafeZoneToPlayer(UUID player, boolean openGui) {
        try {
            PacketHandler.sendPacketToPlayer(new SafeZonePacket(player, From.SERVER, new ObjectMapper().writeValueAsString(SafeZoneManager.getSafeZones().toArray()), openGui), FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(player));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        String toSend = Joiner.on('#').join(uuid, side.toString(), json != null ? json : "", openGui);
        buf.writeBytes(toSend.getBytes());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Charset charset = Charset.forName("ISO-8859-1");
        String received = charset.decode(buf.nioBuffer()).toString();
        List<String> list = Splitter.on('#').splitToList(received);

        if (list.size() > 0)
            this.uuid = list.get(0);
        if (list.size() > 1)
            this.side = From.valueOf(list.get(1));
        if (list.size() > 2)
            this.json = list.get(2);
        if (list.size() > 3)
            this.openGui = (list.get(3) != null && Boolean.parseBoolean(list.get(3)));
    }

    public enum From {
        SERVER,
        CLIENT
    }

    public static class SafeZonePacketHandler implements IMessageHandler<SafeZonePacket, SafeZonePacket> {

        @Override
        public SafeZonePacket onMessage(SafeZonePacket message, MessageContext ctx) {
            switch (message.side) {
                case SERVER:
                    try {
                        if (FMLCommonHandler.instance().getEffectiveSide().isClient()
                                && Minecraft.getMinecraft().player.getUniqueID().equals(UUID.fromString(message.uuid))) {
                            SafeZoneNetworkHandler.setZones(new ArrayList<SafeZone>(Arrays.asList(new ObjectMapper().readValue(message.json, SafeZone[].class))));
                            SafeZoneNetworkHandler.zonesLoaded(message.openGui);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CLIENT:
                    if (FMLCommonHandler.instance().getEffectiveSide().equals(Side.SERVER))
                        try {
                            return new SafeZonePacket(UUID.fromString(message.uuid), From.SERVER, new ObjectMapper().writeValueAsString(SafeZoneManager.getSafeZones().toArray()), message.openGui);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    break;
            }
            return null;
        }
    }
}
