package fr.qilat.prisonrp.server.game.bite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Qilat on 28/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class BiteManager {
    private static BiteManager instance;
    private static HashMap<Bite, EntityPlayer> runningList = new HashMap<Bite, EntityPlayer>();
    private static ArrayList<Potion> effects = new ArrayList<Potion>(
            Arrays.asList(
                    new Potion[]{
                            MobEffects.NAUSEA, MobEffects.HUNGER, MobEffects.WEAKNESS,
                            MobEffects.BLINDNESS, MobEffects.SLOWNESS, MobEffects.POISON
                    }
            )
    );

    private BiteManager() {
    }

    public static void init() {
        if (instance == null) {
            instance = new BiteManager();
            MinecraftForge.EVENT_BUS.register(instance);
        }
    }

    public static void bite(EntityPlayer player) {
        Bite bite = new Bite(player);
        bite.startBite();
        BiteManager.getRunningList().put(bite, player);
    }

    public static boolean isPlayerAffectByBite(EntityPlayer player) {
        for (Map.Entry<Bite, EntityPlayer> entry : BiteManager.runningList.entrySet())
            if (entry.getValue().equals(player))
                return true;
        return false;
    }

    public static void stopPlayersBite(EntityPlayer player) {
        for (Map.Entry<Bite, EntityPlayer> entry : BiteManager.runningList.entrySet())
            if (entry.getValue().equals(player))
                entry.getKey().stopBite();
    }

    public static Potion getRandomPotion() {
        int i = new Random().nextInt(BiteManager.effects.size());
        return BiteManager.effects.get(i);
    }

    public static ArrayList<Potion> getEffects() {
        return BiteManager.effects;
    }


    public static HashMap<Bite, EntityPlayer> getRunningList() {
        return BiteManager.runningList;
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (isPlayerAffectByBite(player)) {

            }

        }
    }
}
