package fr.qilat.prisonrp;

import fr.qilat.prisonrp.server.utils.Logger;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Qilat on 19/11/2017 for PoudlardForge.
 */
public class RegisteringHandler {

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        Logger.info("Registering Items...");
        event.getRegistry().registerAll(PrisonRPItems.WAND);

        Logger.info("Registering Items finish.");
    }
}
