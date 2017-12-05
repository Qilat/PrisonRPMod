package fr.qilat.prisonrp.client.render.entity.player;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

/**
 * Created by Qilat on 21/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class PlayerSkin {
    MinecraftProfileTexture profileTexture = new MinecraftProfileTexture("http://skin.prisonrp.fr/", new HashMap<String, String>());

}
