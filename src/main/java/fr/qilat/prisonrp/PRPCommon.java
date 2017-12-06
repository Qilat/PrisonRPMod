package fr.qilat.prisonrp;

import fr.qilat.prisonrp.server.entity.EntityManager;
import fr.qilat.prisonrp.server.game.bite.BiteManager;
import fr.qilat.prisonrp.server.utils.Logger;
import fr.qilat.prisonrp.server.utils.task.TaskManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * Created by Qilat on 13/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class PRPCommon {

    public void preInit(File configFile) {
        TaskManager.load();
        CustomLootTable.init();
    }


    public void init() {
        MinecraftForge.EVENT_BUS.register(new RegisteringHandler());
        Logger.info("RegisteringHandler load.");
        BiteManager.init();
        Logger.info("BiteManager load.");
        EntityManager.registerEntities();
        Logger.info("Entities regitered.");
    }

    public void postInit() {

    }
}
