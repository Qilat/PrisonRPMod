package fr.qilat.prisonrp.server.utils.task;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Qilat on 26/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class TaskManager {
    static long serverTick = 0;
    static long clientTick = 0;
    private static TaskManager instance;
    private static int taskID = 0;
    private static HashMap<Task, Side> tasks = new HashMap<Task, Side>();
    private static Set<Task> toRemove = new HashSet<Task>();

    public static void load() {
        if (instance == null) {
            instance = new TaskManager();
            MinecraftForge.EVENT_BUS.register(instance);
        }
    }

    public static void cancelAll() {
        tasks = new HashMap<Task, Side>();
    }

    public static void cancelTask(int id) {
        for (Task t : tasks.keySet())
            if (t.getId() == id)
                toRemove.add(t);
    }

    static int getNewTaskId() {
        return taskID++;
    }

    public static TaskManager get() {
        return instance;
    }

    private static Set<Task> keyFor(Side s) {
        Set<Task> set = new HashSet<Task>();
        for (Map.Entry<Task, Side> entry : tasks.entrySet())
            if (entry.getValue().equals(s))
                set.add(entry.getKey());
        return set;
    }

    protected static void addTaskToScheduler(Side s, Task t) {
        tasks.put(t, s);
    }

    public static long getServerTick() {
        return serverTick;
    }

    public static long getClientTick() {
        return clientTick;
    }

    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void scheduler(TickEvent.ServerTickEvent e) {
        Set<Task> finishedTask = scheduleTasks(keyFor(Side.SERVER), serverTick);
        for (Task task : finishedTask)
            tasks.remove(task);
        for (Task task : toRemove)
            tasks.remove(task);

        serverTick++;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void scheduler(TickEvent.ClientTickEvent e) {
        Set<Task> finishedTask = scheduleTasks(keyFor(Side.CLIENT), clientTick);
        for (Task task : finishedTask)
            tasks.remove(task);
        for (Task task : toRemove)
            tasks.remove(task);

        clientTick++;
    }

    public Set<Task> scheduleTasks(Set<Task> set, long time) {
        Set<Task> toRemove = new HashSet<Task>();
        for (Task t : set) {
            if (t.getDelayLater() + (t.getCreateDate() + t.getDelayTimer() * t.getNbOfRun()) <= time) {
                t.run();
                t.nbOfRun++;
                if (t.getDelayTimer() == 0)
                    toRemove.add(t);
            }
        }
        return toRemove;
    }

}
