package fr.qilat.prisonrp.server.game.safezone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.qilat.prisonrp.PrisonRPCore;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Qilat on 28/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class SafeZoneManager {
    private static final String SAVE_FILE_NAME = "safezone.json";
    public static final File SAVE_FILE = new File(PrisonRPCore.configDirectory, SAVE_FILE_NAME);

    static int currentId = 0;
    static ArrayList<SafeZone> safeZones = new ArrayList<SafeZone>();

    public static void load(File file) {
        try {
            File safezoneFile = file;
            if (safezoneFile.exists())
                safeZones = new ArrayList<SafeZone>(Arrays.asList(new ObjectMapper().readValue(file, SafeZone[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupCurrentID();
    }

    public static void setupCurrentID() {
        currentId = safeZones.size();
    }

    public static void save(File file) {
        try {
            new ObjectMapper().writeValue(file, safeZones);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInSafeZone(BlockPos pos) {
        for (SafeZone zone : safeZones)
            if (zone.contains(pos))
                return true;
        return false;
    }

    public static ArrayList<SafeZone> getSafeZones() {
        return safeZones;
    }

    public static int create(BlockPos pos1, BlockPos pos2) {
        SafeZone safeZone = new SafeZone(pos1, pos2);
        safeZones.add(safeZone);
        save(SAVE_FILE);
        return safeZone.getId();
    }

    public static boolean exist(int id) {
        for (SafeZone safeZone : safeZones)
            if (safeZone.getId() == id)
                return true;
        return false;
    }

    public static boolean delete(int id) {
        for (SafeZone safeZone : safeZones)
            if (safeZone.getId() == id) {
                safeZones.remove(safeZone);
                save(SAVE_FILE);
                return true;
            }
        return false;
    }

    public static SafeZone get(int id) {
        for (SafeZone safeZone : safeZones)
            if (id == safeZone.getId())
                return safeZone;
        return null;
    }
}
