package fr.qilat.prisonrp;

import fr.qilat.prisonrp.server.item.Wand;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Qilat on 19/11/2017 for PoudlardForge.
 */
public class PrisonRPItems {

    public static final Item WAND = new Wand();


    public static void setItemName(Item item, String name) {
        item.setRegistryName(PrisonRPCore.MODID, name).setUnlocalizedName(PrisonRPCore.MODID + "." + name);
    }


    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        registerModel(WAND, 0);

    }


    @SideOnly(Side.CLIENT)
    public static void registerModel(Item item, int metadata) {
        if (metadata < 0) metadata = 0;

        String resourceName = item.getUnlocalizedName().substring(5).replace('.', ':');

        if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);

        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(resourceName, "inventory"));
    }

}
