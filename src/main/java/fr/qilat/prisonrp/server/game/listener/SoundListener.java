package fr.qilat.prisonrp.server.game.listener;

import com.google.common.base.Predicate;
import fr.qilat.prisonrp.server.entity.EntityCZombie;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;


/**
 * Created by Qilat on 26/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class SoundListener {

    int xradius = 200;
    int yradius = 200;
    int zradius = 200;

    @SubscribeEvent
    public void onSound(PlaySoundAtEntityEvent event) {
        String soundName = event.getSound().getRegistryName().toString();

        if (event.getEntity() != null && soundName.contains("mw")) {
            if (soundName.contains("silenced"))
                return;

            final double soundPosX = event.getEntity().posX;
            final double soundPosY = event.getEntity().posY;
            final double soundPosZ = event.getEntity().posZ;
            final int[] i = {0};
            List<EntityCZombie> list = event.getEntity().world.getEntities(EntityCZombie.class, new Predicate<EntityCZombie>() {
                @Override
                public boolean apply(@Nullable EntityCZombie input) {
                    if (input != null)
                        if (Math.abs(input.posX - soundPosX) < xradius
                                && Math.abs(input.posY - soundPosY) < yradius
                                && Math.abs(input.posZ - soundPosZ) < zradius) {
                            return true;
                        }
                    return false;
                }
            });

            for (EntityCZombie zombie : list) {
                zombie.getNavigator().tryMoveToXYZ(soundPosX, soundPosY, soundPosZ, zombie.getAIMoveSpeed());
            }

        }
    }

}