package fr.qilat.prisonrp.server.utils;

import fr.qilat.prisonrp.PrisonRPCore;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * Created by Qilat on 25/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class Utils {
    public static Logger getLogger() {
        return PrisonRPCore.logger;
    }

    public static String readFile(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void broadcastMessage(String msg) {
        FMLCommonHandler.instance().getMinecraftServerInstance().sendMessage(new TextComponentString(msg));
    }
}
