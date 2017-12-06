package fr.qilat.prisonrp.server.game.bite;

import fr.qilat.prisonrp.server.utils.task.Task;
import fr.qilat.prisonrp.server.utils.task.TaskManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Qilat on 26/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class Bite {

    private PotionEffect currentEffect;
    private EntityPlayer player;

    private int effectTask;

    protected Bite(EntityPlayer player) {
        this.player = player;

    }

    void startBite() {
        this.effectTask = new Task(Side.SERVER) {
            @Override
            public void run() {
                player.addPotionEffect(Bite.this.currentEffect = new PotionEffect(BiteManager.getRandomPotion(), 120 * 20, 1));
                if (this.getNbOfRun() == (10 * 60) / 120) {
                    stopBite();
                    player.setDead();
                }
            }
        }.runTaskTimer(120 * 20L, 0L);
    }

    public void stopBite() {
        TaskManager.cancelTask(this.effectTask);
        BiteManager.getRunningList().remove(this);
        player.removePotionEffect(getCurrentEffect().getPotion());
    }

    public PotionEffect getCurrentEffect() {
        return currentEffect;
    }
}
