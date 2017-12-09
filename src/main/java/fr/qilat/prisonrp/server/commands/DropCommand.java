package fr.qilat.prisonrp.server.commands;

import fr.qilat.prisonrp.server.game.drop.Drop;
import fr.qilat.prisonrp.server.utils.Location;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Qilat on 03/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class DropCommand extends CommandBase {
    public static final String NAME = "drop";
    public static final String USAGE = "/drop";

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
        return !(sender instanceof EntityPlayer) || PermissionAPI.hasPermission((EntityPlayer) sender, "prisonrp.command.drop");
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 0)
            throw new WrongUsageException(this.getUsage(sender));

        BlockPos pos = sender.getPosition();
        new Drop(new Location(sender.getEntityWorld(), pos));

        sender.sendMessage(new TextComponentString("Vous venez de faire apparaitre un colis en " + pos + "."));
    }
}