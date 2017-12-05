package fr.qilat.prisonrp.server;

import fr.qilat.prisonrp.PRPCommon;
import fr.qilat.prisonrp.server.game.listener.BiteListener;
import fr.qilat.prisonrp.server.game.listener.SafeZoneListener;
import fr.qilat.prisonrp.server.game.listener.SoundListener;
import fr.qilat.prisonrp.server.utils.Logger;
import fr.qilat.prisonrp.server.utils.Utils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

/**
 * Created by Qilat on 14/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class PRPServer extends PRPCommon {
    @Override
    public void preInit(File configFile) {
        super.preInit(configFile);
        Logger.info("Server pre-init");
    }

    @Override
    public void init() {
        super.init();
        MinecraftForge.EVENT_BUS.register(new SoundListener());
        MinecraftForge.EVENT_BUS.register(new SafeZoneListener());
        MinecraftForge.EVENT_BUS.register(new BiteListener());
        Logger.info("Server init");
    }

    @Override
    public void postInit() {
        super.postInit();
        //TODO launch drop
        Logger.info("Server post-init");
    }

}
