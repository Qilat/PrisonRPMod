package fr.qilat.prisonrp.client;

import fr.qilat.prisonrp.PRPCommon;
import fr.qilat.prisonrp.PrisonRPItems;
import fr.qilat.prisonrp.client.listener.GuiListener;
import fr.qilat.prisonrp.client.listener.InputListener;
import fr.qilat.prisonrp.client.listener.RenderListener;
import fr.qilat.prisonrp.server.entity.EntityManager;
import fr.qilat.prisonrp.server.utils.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

/**
 * Created by Qilat on 13/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class PRPClient extends PRPCommon {

    @Override
    public void preInit(File configFile) {
        super.preInit(configFile);
        PrisonRPItems.registerItemModels();

    }

    @Override
    public void init() {
        super.init();

        EntityManager.registerAllEntityModels();
        Logger.info("EntityModels load.");

        MinecraftForge.EVENT_BUS.register(new GuiListener());
        MinecraftForge.EVENT_BUS.register(new RenderListener());
        MinecraftForge.EVENT_BUS.register(new InputListener());
        Logger.info("Listeners load.");
    }

    @Override
    public void postInit() {
        super.postInit();
    }


}
