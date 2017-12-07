package fr.qilat.prisonrp;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class CustomLootTable {
    public static ResourceLocation CZOMBIE;

    public static void init() {
        CZOMBIE = LootTableList.register(new ResourceLocation(PrisonRPCore.MODID, "entities/czombie"));
    }
}
