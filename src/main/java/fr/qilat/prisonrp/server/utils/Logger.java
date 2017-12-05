package fr.qilat.prisonrp.server.utils;

/**
 * Created by Qilat on 25/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class Logger {
    public static void info(String msg) {
        Utils.getLogger().info(msg);
    }

    public static void warn(String msg) {
        Utils.getLogger().warn(msg);
    }

    public static void error(String msg) {
        Utils.getLogger().error(msg);
    }
}
