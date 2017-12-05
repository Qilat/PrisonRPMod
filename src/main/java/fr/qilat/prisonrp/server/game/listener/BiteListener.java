package fr.qilat.prisonrp.server.game.listener;

import fr.qilat.prisonrp.server.entity.EntityCZombie;
import fr.qilat.prisonrp.server.game.BiteManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Qilat on 28/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class BiteListener {

    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        Entity source = event.getSource().getSourceOfDamage();
        Entity target = event.getEntity();
        if (!event.isCanceled()
                && source != null
                && target != null
                && source instanceof EntityCZombie
                && target instanceof EntityPlayer) {
            BiteManager.bite((EntityPlayer) target);
        }
    }

}
