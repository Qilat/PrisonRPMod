package fr.qilat.prisonrp.server.utils.task;

import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Qilat on 26/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public abstract class Task implements Runnable {
    public long nbOfRun = 0;
    private long createDate;
    private long delayTimer = 0;
    private long delayLater = 0;
    private int id = TaskManager.getNewTaskId();
    private Side side;

    public Task(Side s) {
        this.side = s;
        this.createDate = s.equals(Side.SERVER) ? TaskManager.getServerTick() : TaskManager.getClientTick();
    }

    public int runTask() {
        TaskManager.addTaskToScheduler(this.side, this);
        return id;
    }

    public int runTaskLater(long delayLater) {
        this.delayLater = delayLater > 0 ? delayLater : 0;
        return this.runTask();
    }

    public int runTaskTimer(long delayTimer, long delayLater) {
        this.delayTimer = delayTimer > 0 ? delayTimer : 0;
        return this.runTaskLater(delayLater);
    }

    public int getId() {
        return id;
    }

    public long getCreateDate() {
        return createDate;
    }

    public long getDelayTimer() {
        return delayTimer;
    }

    public long getDelayLater() {
        return delayLater;
    }

    public long getNbOfRun() {
        return nbOfRun;
    }

}

