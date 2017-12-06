package fr.qilat.prisonrp;

import fr.qilat.prisonrp.server.commands.DropCommand;
import fr.qilat.prisonrp.server.commands.PosCommand;
import fr.qilat.prisonrp.server.commands.SafeZoneCommand;
import fr.qilat.prisonrp.server.commands.ZombieCommand;
import fr.qilat.prisonrp.server.game.safezone.SafeZoneManager;
import fr.qilat.prisonrp.server.utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

import java.io.File;

/**
 * Created by Qilat on 13/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@Mod(modid = PrisonRPCore.MODID, name = PrisonRPCore.NAME, version = PrisonRPCore.VERSION, acceptedMinecraftVersions = PrisonRPCore.ACCEPTED_MC_VERSIONS)
public class PrisonRPCore {
    public static final String MODID = "prisonrp";
    public static final String NAME = "PrisonRP";
    public static final String VERSION = "1.0";
    public static final String ACCEPTED_MC_VERSIONS = "[1.10.2]";

    @Mod.Instance(PrisonRPCore.MODID)
    public static PrisonRPCore instance;

    @SidedProxy(clientSide = "fr.qilat.prisonrp.client.PRPClient", serverSide = "fr.qilat.prisonrp.server.PRPServer")
    public static PRPCommon proxy;

    public static org.apache.logging.log4j.Logger logger;

    public static File configDirectory;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
        configDirectory = event.getModConfigurationDirectory();
        Logger.info("Pre-Init done");
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();

        PermissionAPI.registerNode("prisonrp.command.zombie", DefaultPermissionLevel.OP, "Allows players to use the zombie spawn command");
        PermissionAPI.registerNode("prisonrp.command.pos", DefaultPermissionLevel.OP, "Allows players to use the position command");
        PermissionAPI.registerNode("prisonrp.command.safezone", DefaultPermissionLevel.OP, "Allows players to use the safezone command");
        PermissionAPI.registerNode("prisonrp.command.drop", DefaultPermissionLevel.OP, "Allows players to use the drop command");

        Logger.info("Init done");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        Logger.info("Post-Init done");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ZombieCommand());
        event.registerServerCommand(new PosCommand());
        event.registerServerCommand(new SafeZoneCommand());
        event.registerServerCommand(new DropCommand());
        Logger.info("Commands load");

        SafeZoneManager.load(SafeZoneManager.SAVE_FILE);
        Logger.info("Load of " + SafeZoneManager.getSafeZones().size() + " SafeZones");

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        SafeZoneManager.save(SafeZoneManager.SAVE_FILE);
        Logger.info("Save of " + SafeZoneManager.getSafeZones().size() + " SafeZones");
    }
}
