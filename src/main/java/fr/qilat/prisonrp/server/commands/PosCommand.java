package fr.qilat.prisonrp.server.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Qilat on 28/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class PosCommand extends CommandBase {
    public static final String NAME = "pos";
    public static final String USAGE = "/pos <1 | 2>";

    public static HashMap<EntityPlayer, BlockPos> pos1HashMap = new HashMap<EntityPlayer, BlockPos>();
    public static HashMap<EntityPlayer, BlockPos> pos2HashMap = new HashMap<EntityPlayer, BlockPos>();

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
        return !(sender instanceof EntityPlayer) || PermissionAPI.hasPermission((EntityPlayer) sender, "prisonrp.command.pos");
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
            throw new WrongUsageException(this.getUsage(sender));

        int posId = parseInt(args[0], 1, 2);
        if (posId == 1) {
            pos1HashMap.put((EntityPlayer) sender, sender.getPosition());
        } else if (posId == 2) {
            pos2HashMap.put((EntityPlayer) sender, sender.getPosition());
        }

        sender.sendMessage(new TextComponentString("Vous venez d'enregistrer cette position en " + posId + "."));
    }
}
