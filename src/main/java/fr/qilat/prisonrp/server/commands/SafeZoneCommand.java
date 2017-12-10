package fr.qilat.prisonrp.server.commands;

import fr.qilat.prisonrp.server.game.safezone.SafeZone;
import fr.qilat.prisonrp.server.game.safezone.SafeZoneManager;
import fr.qilat.prisonrp.server.network.packets.SafeZonePacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Qilat on 28/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class SafeZoneCommand extends CommandBase {
    public static final String NAME = "sfz";
    public static final String USAGE = "/sfz <create [name] | delete [id] | list | info> ";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        for (String str : sender.getServer().getPlayerList().getOppedPlayerNames()) {
            if (str == sender.getName()) {
                return USAGE;
            }
        }
        return null;
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>(Arrays.asList(new String[]{NAME}));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return !(sender instanceof EntityPlayer) || PermissionAPI.hasPermission((EntityPlayer) sender, "prisonrp.command.safezone");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1)
            throw new WrongUsageException(this.getUsage(sender));
        if (!(sender instanceof EntityPlayerMP))
            throw new WrongUsageException("Impossible depuis cette entité.");

        String subCommand = args[0];

        if (subCommand.equals("create")) {
            if (args.length > 2)
                throw new WrongUsageException(this.getUsage(sender));
            if(PosCommand.pos1HashMap.get(sender) == null || PosCommand.pos2HashMap.get(sender) == null)
                throw new CommandException("Vous n'avez pas sélectionné deux positions à l'aide de la commande /pos <1|2>.");

            int id = SafeZoneManager.create(PosCommand.pos1HashMap.get(sender), PosCommand.pos2HashMap.get(sender));
            sender.sendMessage(new TextComponentString("Vous avez créer une safezone. ID : " + id));
        } else if (subCommand.equals("list")) {
            if (args.length != 1)
                throw new WrongUsageException(this.getUsage(sender));
            EntityPlayerMP player = (EntityPlayerMP) sender;
            SafeZonePacket.sendSafeZoneToPlayer(player.getUniqueID(), true);

        } else if (subCommand.equals("delete")) {
            if (args.length != 2)
                throw new WrongUsageException(this.getUsage(sender));
            int safezoneId = parseInt(args[1]);
            if (SafeZoneManager.exist(safezoneId)) {
                SafeZoneManager.delete(safezoneId);
                sender.sendMessage(new TextComponentString("Vous venez de supprimer la zone n°" + safezoneId + "."));
            } else {
                throw new WrongUsageException("Zone inexistance");
            }
        } else if (subCommand.equals("info")) {
            if (args.length != 2)
                throw new WrongUsageException(this.getUsage(sender));
            int safezoneId = parseInt(args[1]);
            if (SafeZoneManager.exist(safezoneId)) {
                SafeZone safeZone = SafeZoneManager.get(safezoneId);
                sender.sendMessage(new TextComponentString("Position n°1 : x=" + safeZone.getPos1X() + " y=" + safeZone.getPos1Y() + " z=" + safeZone.getPos1Z()));
                sender.sendMessage(new TextComponentString("Position n°2 : x=" + safeZone.getPos2X() + " y=" + safeZone.getPos2Y() + " z=" + safeZone.getPos2Z()));
            } else {
                throw new WrongUsageException("Zone inexistante");
            }
        } else if(subCommand.equals("reload")){

        }
    }
}
