package fr.qilat.prisonrp.server.game.listener;

import fr.qilat.prisonrp.server.entity.EntityCZombie;
import fr.qilat.prisonrp.server.game.safezone.SafeZoneManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Qilat on 29/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class SafeZoneListener {

    @SubscribeEvent
    public void onSpawn(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityCZombie) {
            if (SafeZoneManager.isInSafeZone(event.getEntity().getPosition())) {
                event.setCanceled(true);
            }
        }
    }

}
