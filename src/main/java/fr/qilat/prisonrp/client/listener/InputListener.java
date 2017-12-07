package fr.qilat.prisonrp.client.listener;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.gui.GuiSafeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

/**
 * Created by Qilat on 06/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class InputListener {
    private static InputListener instance = null;

    private static KeyBinding keyBindSafeZone;

    public InputListener() {
        if (InputListener.instance == null) {
            instance = this;

            keyBindSafeZone = new KeyBinding(PrisonRPCore.MODID + ".key.safezone", Keyboard.KEY_M, "key.categories.inventory");
            ClientRegistry.registerKeyBinding(keyBindSafeZone);
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onEvent(InputEvent.KeyInputEvent event) {
        if (keyBindSafeZone.isPressed()) {
            keyTestTyped();
        }
    }

    private void keyTestTyped() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiSafeZone());
    }


}
