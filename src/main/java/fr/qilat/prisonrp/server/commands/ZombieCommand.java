package fr.qilat.prisonrp.server.commands;

import fr.qilat.prisonrp.server.entity.EntityCZombie;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
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
public class ZombieCommand extends CommandBase {
    public static final String NAME = "zombie";
    public static final String USAGE = "/zombie <nombre | defaut = 1>";

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
        return new ArrayList<String>(Arrays.asList(new String[]{NAME, "z"}));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return !(sender instanceof EntityPlayer) || PermissionAPI.hasPermission((EntityPlayer) sender, "prisonrp.command.zombie");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
            throw new WrongUsageException(this.getUsage(sender));

        int amount = parseInt(args[0], 0, 200);

        for (int i = 0; i < amount; i++) {
            EntityCZombie cZombie = new EntityCZombie(sender.getEntityWorld());
            cZombie.setLocationAndAngles(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ(), 0f, 0f);
            sender.getEntityWorld().spawnEntity(cZombie);
        }

        sender.sendMessage(new TextComponentString("Vous avez fait apparaitre " + amount + " zombies."));


    }
}
