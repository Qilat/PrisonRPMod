package fr.qilat.prisonrp.server.sound;

import fr.qilat.prisonrp.PrisonRPCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

/**
 * Created by Qilat on 16/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class CustomSoundHandler {

    public static SoundEvent musicMainMenu;

    private static int size = 0;

    public static void init() {
        //   size = SoundEvent.REGISTRY.getKeys().size();
        //   musicMainMenu = register("404Serpents");
    }

    public static SoundEvent register(String name) {
        ResourceLocation loc = new ResourceLocation(PrisonRPCore.MODID, name);
        SoundEvent e = new SoundEvent(loc);

        SoundEvent.REGISTRY.register(size, loc, e);
        size++;

        return e;
    }

}
