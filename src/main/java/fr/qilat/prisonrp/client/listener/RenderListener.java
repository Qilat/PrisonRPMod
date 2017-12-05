package fr.qilat.prisonrp.client.listener;

import fr.qilat.prisonrp.client.render.entity.player.CustomRenderPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Qilat on 22/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.CLIENT)
public class RenderListener {

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (!(event.getRenderer() instanceof CustomRenderPlayer)) {
            event.setCanceled(true);
            EntityPlayer user = Minecraft.getMinecraft().player;
            EntityPlayer rendered = event.getEntityPlayer();

            float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
            double x = (rendered.lastTickPosX + (rendered.posX - rendered.lastTickPosX) * (double) partialTicks) - (user.lastTickPosX + (user.posX - user.lastTickPosX) * (double) partialTicks);
            double y = (rendered.lastTickPosY + (rendered.posY - rendered.lastTickPosY) * (double) partialTicks) - (user.lastTickPosY + (user.posY - user.lastTickPosY) * (double) partialTicks);
            double z = (rendered.lastTickPosZ + (rendered.posZ - rendered.lastTickPosZ) * (double) partialTicks) - (user.lastTickPosZ + (user.posZ - user.lastTickPosZ) * (double) partialTicks);
            float yaw = rendered.prevRotationYaw + (rendered.rotationYaw - rendered.prevRotationYaw) * partialTicks;

            new CustomRenderPlayer(event.getRenderer().getRenderManager())
                    .doRender((AbstractClientPlayer) event.getEntityPlayer(), x, y, z, yaw, partialTicks);
        }
    }

}
