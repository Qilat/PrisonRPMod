package fr.qilat.prisonrp;

import fr.qilat.prisonrp.server.game.safezone.SafeZoneManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

/**
 * Created by Qilat on 26/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class Test {

    public static void main(String[] args) {
        SafeZoneManager.create(new BlockPos(1, 1, 1), new BlockPos(2, 2, 2));
        SafeZoneManager.save(new File("safezone.save"));
    }
}
