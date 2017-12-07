package fr.qilat.prisonrp.server.network.packets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZoneManager;
import fr.qilat.prisonrp.server.network.SafeZoneNetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.actors.threadpool.Arrays;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class SafeZonePacket implements IMessage {
    private From side;
    private String json = null;
    public SafeZonePacket() {
    }

    public SafeZonePacket(From side, String json) {
        this.side = side;
        this.json = json;
    }

    public SafeZonePacket(From side) {
        this(side, null);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        String toSend = Joiner.on('#').join(side.toString(), json != null ? json : "");
        buf.writeBytes(toSend.getBytes());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Charset charset = Charset.forName("ISO-8859-1");
        String received = charset.decode(buf.nioBuffer()).toString();
        List<String> list = Splitter.on('#').splitToList(received);

        if (list.size() > 0)
            this.side = From.valueOf(list.get(0));
        if (list.size() > 1)
            this.json = list.get(1);
    }

    public enum From {
        SERVER,
        CLIENT
    }

    public static class SafeZonePacketHandler implements IMessageHandler<SafeZonePacket, IMessage> {

        @Override
        public IMessage onMessage(SafeZonePacket message, MessageContext ctx) {
            System.out.println("packet received side :" + message.side);
            switch (message.side) {
                case SERVER:
                    try {
                        SafeZoneNetworkHandler.setZones(new ArrayList<SafeZone>(Arrays.asList(new ObjectMapper().readValue(message.json, SafeZone[].class))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CLIENT:
                    try {
                        if(!Minecraft.getMinecraft().world.isRemote)
                            return new SafeZonePacket(From.CLIENT, new ObjectMapper().writeValueAsString(SafeZoneManager.getSafeZones().toArray()));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return null;
        }
    }
}
