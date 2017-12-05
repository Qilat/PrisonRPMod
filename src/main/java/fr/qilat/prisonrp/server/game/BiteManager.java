package fr.qilat.prisonrp.server.game;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Qilat on 28/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class BiteManager {

    private static HashMap<Bite, EntityPlayer> runningList = new HashMap<Bite, EntityPlayer>();
    private static ArrayList<Potion> effects = new ArrayList<Potion>(
            Arrays.asList(
                    new Potion[]{
                            MobEffects.NAUSEA, MobEffects.HUNGER, MobEffects.WEAKNESS,
                            MobEffects.BLINDNESS, MobEffects.SLOWNESS, MobEffects.POISON
                    }
            )
    );

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

}
