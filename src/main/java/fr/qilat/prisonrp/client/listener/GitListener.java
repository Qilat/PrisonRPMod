/*
package morph.common.core;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class GitListener
{

    public boolean forcedSpecialRenderCall;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderPlayer(RenderPlayerEvent.Pre event)
    {
        float shadowSize = 0.5F;
        try
        {
            if(Morph.proxy.tickHandlerClient.playerRenderShadowSize < 0.0F)
            {
                Morph.proxy.tickHandlerClient.playerRenderShadowSize = event.renderer.shadowSize;
            }
        }
        catch(Exception e)
        {
            ObfHelper.obfWarning();
            e.printStackTrace();
        }

        if(Morph.proxy.tickHandlerClient.allowRender)
        {
            Morph.proxy.tickHandlerClient.allowRender = false;
            event.renderer.shadowSize = Morph.proxy.tickHandlerClient.playerRenderShadowSize;
            return;
        }
        if(Morph.proxy.tickHandlerClient.forceRender)
        {
            event.renderer.shadowSize = Morph.proxy.tickHandlerClient.playerRenderShadowSize;
            return;
        }
        if(Morph.proxy.tickHandlerClient.renderingMorph && Morph.proxy.tickHandlerClient.renderingPlayer > 1)
        {
            event.setCanceled(true);
            return;
        }

        Morph.proxy.tickHandlerClient.renderingPlayer++;

        if(Morph.proxy.tickHandlerClient.playerMorphInfo.containsKey(event.entityPlayer.getCommandSenderName()))
        {
            MorphInfoClient info = Morph.proxy.tickHandlerClient.playerMorphInfo.get(event.entityPlayer.getCommandSenderName());
            if(!event.entityPlayer.isPlayerSleeping() && event.entityPlayer.worldObj.playerEntities.contains(event.entityPlayer))
            {
                info.player = event.entityPlayer;
            }

            double par2 = Morph.proxy.tickHandlerClient.renderTick;
            int br1 = info.prevState != null ? info.prevState.entInstance.getBrightnessForRender((float)par2) : event.entityPlayer.getBrightnessForRender((float)par2);
            int br2 = info.nextState.entInstance.getBrightnessForRender((float)par2);

            float prog = (float)(info.morphProgress + par2) / 80F;

            if(prog > 1.0F)
            {
                prog = 1.0F;
            }

            try
            {
                float prevShadowSize = Morph.proxy.tickHandlerClient.playerRenderShadowSize;

                if(info.prevState != null && info.prevState.entInstance != null)
                {
                    Render render = RenderManager.instance.getEntityRenderObject(info.prevState.entInstance);
                    if(render != null)
                    {
                        prevShadowSize = render.shadowSize;
                    }
                }

                Render render = RenderManager.instance.getEntityRenderObject(info.nextState.entInstance);
                if(render == event.renderer)
                {
                    shadowSize = Morph.proxy.tickHandlerClient.playerRenderShadowSize;
                }
                else if(render != null)
                {
                    shadowSize = render.shadowSize;
                }

                float shadowProg = prog;
                shadowProg /= 0.8F;
                if(shadowProg < 1.0F)
                {
                    shadowSize = prevShadowSize + (shadowSize - prevShadowSize) * prog;
                }
                render.shadowSize = shadowSize;
            }
            catch(Exception e)
            {
                ObfHelper.obfWarning();
                e.printStackTrace();
            }

            if(Morph.proxy.tickHandlerClient.renderingPlayer != 2)
            {
                event.setCanceled(true);
            }
            else
            {
                Morph.proxy.tickHandlerClient.renderingPlayer--;
                return;
            }

            double d0 = event.entityPlayer.lastTickPosX + (event.entityPlayer.posX - event.entityPlayer.lastTickPosX) * (double)par2;
            double d1 = event.entityPlayer.lastTickPosY + (event.entityPlayer.posY - event.entityPlayer.lastTickPosY) * (double)par2;
            double d2 = event.entityPlayer.lastTickPosZ + (event.entityPlayer.posZ - event.entityPlayer.lastTickPosZ) * (double)par2;
            float f1 = event.entityPlayer.prevRotationYaw + (event.entityPlayer.rotationYaw - event.entityPlayer.prevRotationYaw) * (float)par2;

            int i = br1 + (int)((float)(br2 - br1) * prog);

            if (event.entityPlayer.isBurning())
            {
                i = 15728880;
            }

            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if(info != null)
            {
                Morph.proxy.tickHandlerClient.renderingMorph = true;
                GL11.glPushMatrix();

                //	        	ObfuscationReflectionHelper.setPrivateValue(RendererLivingEntity.class, info.prevEntInfo.entRender, info.prevEntModel, ObfHelper.mainModel);

                //	        	event.entityPlayer.yOffset -= Morph.proxy.tickHandlerClient.ySize;

                GL11.glTranslated(1 * (d0 - RenderManager.renderPosX), 1 * (d1 - RenderManager.renderPosY) + (event.entityPlayer == Minecraft.getMinecraft().thePlayer && !((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F) ? Morph.proxy.tickHandlerClient.ySize : 0D), 1 * (d2 - RenderManager.renderPosZ));

                //	        	GL11.glScalef(1.0F, -1.0F, -1.0F);

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                float renderTick = Morph.proxy.tickHandlerClient.renderTick;

                float prevEntSize = info.prevState.entInstance != null ? info.prevState.entInstance.width > info.prevState.entInstance.height ? info.prevState.entInstance.width : info.prevState.entInstance.height : 1.0F;
                float nextEntSize = info.nextState.entInstance != null ? info.nextState.entInstance.width > info.nextState.entInstance.height ? info.nextState.entInstance.width : info.nextState.entInstance.height : 1.0F;

                float prevScaleMag = prevEntSize > 2.5F ? (2.5F / prevEntSize) : 1.0F;
                float nextScaleMag = nextEntSize > 2.5F ? (2.5F / nextEntSize) : 1.0F;

                if(info.morphProgress <= 40)
                {
                    if(info.prevModelInfo != null && info.morphProgress < 10)
                    {
                        float ff2 = info.prevState.entInstance.renderYawOffset;
                        float ff3 = info.prevState.entInstance.rotationYaw;
                        float ff4 = info.prevState.entInstance.rotationPitch;
                        float ff5 = info.prevState.entInstance.prevRotationYawHead;
                        float ff6 = info.prevState.entInstance.rotationYawHead;

                        if((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F)
                        {
                            GL11.glScalef(prevScaleMag, prevScaleMag, prevScaleMag);

                            EntityLivingBase renderView = Minecraft.getMinecraft().renderViewEntity;

                            info.prevState.entInstance.renderYawOffset = renderView.renderYawOffset;
                            info.prevState.entInstance.rotationYaw = renderView.rotationYaw;
                            info.prevState.entInstance.rotationPitch = renderView.rotationPitch;
                            info.prevState.entInstance.prevRotationYawHead = renderView.prevRotationYawHead;
                            info.prevState.entInstance.rotationYawHead = renderView.rotationYawHead;
                            renderTick = 1.0F;
                        }

                        info.prevModelInfo.forceRender(info.prevState.entInstance, 0.0D, 0.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);

                        if(info.getMorphing())
                        {
                            float progress = ((float)info.morphProgress + Morph.proxy.tickHandlerClient.renderTick) / 10F;

                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                            GL11.glColor4f(1.0F, 1.0F, 1.0F, progress);

                            ResourceLocation resourceLoc = ObfHelper.invokeGetEntityTexture(info.prevModelInfo.getRenderer(), info.prevModelInfo.getRenderer().getClass(), info.prevState.entInstance);
                            String resourceDomain = ReflectionHelper.getPrivateValue(ResourceLocation.class, resourceLoc, ObfHelper.resourceDomain);
                            String resourcePath = ReflectionHelper.getPrivateValue(ResourceLocation.class, resourceLoc, ObfHelper.resourcePath);

                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, "morph", ObfHelper.resourceDomain);
                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, "textures/skin/morphskin.png", ObfHelper.resourcePath);

                            info.prevModelInfo.forceRender(info.prevState.entInstance, 0.0D, 0.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);

                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, resourceDomain, ObfHelper.resourceDomain);
                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, resourcePath, ObfHelper.resourcePath);

                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        }

                        info.prevState.entInstance.renderYawOffset = ff2;
                        info.prevState.entInstance.rotationYaw = ff3;
                        info.prevState.entInstance.rotationPitch = ff4;
                        info.prevState.entInstance.prevRotationYawHead = ff5;
                        info.prevState.entInstance.rotationYawHead = ff6;
                    }
                }
                else
                {
                    if(info.nextModelInfo != null && info.morphProgress >= 70)
                    {
                        float ff2 = info.nextState.entInstance.renderYawOffset;
                        float ff3 = info.nextState.entInstance.rotationYaw;
                        float ff4 = info.nextState.entInstance.rotationPitch;
                        float ff5 = info.nextState.entInstance.prevRotationYawHead;
                        float ff6 = info.nextState.entInstance.rotationYawHead;

                        if((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F)
                        {
                            GL11.glScalef(nextScaleMag, nextScaleMag, nextScaleMag);

                            EntityLivingBase renderView = Minecraft.getMinecraft().renderViewEntity;

                            info.nextState.entInstance.prevRenderYawOffset = info.nextState.entInstance.renderYawOffset = renderView.renderYawOffset;
                            info.nextState.entInstance.rotationYaw = renderView.rotationYaw;
                            info.nextState.entInstance.rotationPitch = renderView.rotationPitch;
                            info.nextState.entInstance.prevRotationYawHead = renderView.prevRotationYawHead;
                            info.nextState.entInstance.rotationYawHead = renderView.rotationYawHead;
                            renderTick = 1.0F;
                        }

                        info.nextModelInfo.forceRender(info.nextState.entInstance, 0.0D, 0.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);

                        if(info.getMorphing())
                        {
                            float progress = ((float)info.morphProgress - 70 + Morph.proxy.tickHandlerClient.renderTick) / 10F;

                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                            if(progress > 1.0F)
                            {
                                progress = 1.0F;
                            }
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - progress);

                            ResourceLocation resourceLoc = ObfHelper.invokeGetEntityTexture(info.nextModelInfo.getRenderer(), info.nextModelInfo.getRenderer().getClass(), info.nextState.entInstance);
                            String resourceDomain = ReflectionHelper.getPrivateValue(ResourceLocation.class, resourceLoc, ObfHelper.resourceDomain);
                            String resourcePath = ReflectionHelper.getPrivateValue(ResourceLocation.class, resourceLoc, ObfHelper.resourcePath);

                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, "morph", ObfHelper.resourceDomain);
                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, "textures/skin/morphskin.png", ObfHelper.resourcePath);

                            info.nextModelInfo.forceRender(info.nextState.entInstance, 0.0D, 0.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);

                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, resourceDomain, ObfHelper.resourceDomain);
                            ReflectionHelper.setPrivateValue(ResourceLocation.class, resourceLoc, resourcePath, ObfHelper.resourcePath);

                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        }

                        info.nextState.entInstance.renderYawOffset = ff2;
                        info.nextState.entInstance.rotationYaw = ff3;
                        info.nextState.entInstance.rotationPitch = ff4;
                        info.nextState.entInstance.prevRotationYawHead = ff5;
                        info.nextState.entInstance.rotationYawHead = ff6;
                    }
                }
                if(info.prevModelInfo != null && info.nextModelInfo != null && info.morphProgress >= 10 && info.morphProgress < 70)
                {
                    float progress = ((float)info.morphProgress - 10F + Morph.proxy.tickHandlerClient.renderTick) / 60F;

                    float ff2 = info.prevState.entInstance.renderYawOffset;
                    float ff3 = info.prevState.entInstance.rotationYaw;
                    float ff4 = info.prevState.entInstance.rotationPitch;
                    float ff5 = info.prevState.entInstance.prevRotationYawHead;
                    float ff6 = info.prevState.entInstance.rotationYawHead;

                    float fff2 = info.nextState.entInstance.renderYawOffset;
                    float fff3 = info.nextState.entInstance.rotationYaw;
                    float fff4 = info.nextState.entInstance.rotationPitch;
                    float fff5 = info.nextState.entInstance.prevRotationYawHead;
                    float fff6 = info.nextState.entInstance.rotationYawHead;

                    if((Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative) && RenderManager.instance.playerViewY == 180.0F)
                    {
                        GL11.glScalef(prevScaleMag + (nextScaleMag - prevScaleMag) * progress, prevScaleMag + (nextScaleMag - prevScaleMag) * progress, prevScaleMag + (nextScaleMag - prevScaleMag) * progress);

                        EntityLivingBase renderView = Minecraft.getMinecraft().renderViewEntity;

                        info.nextState.entInstance.renderYawOffset = info.prevState.entInstance.renderYawOffset = renderView.renderYawOffset;
                        info.nextState.entInstance.rotationYaw = info.prevState.entInstance.rotationYaw = renderView.rotationYaw;
                        info.nextState.entInstance.rotationPitch = info.prevState.entInstance.rotationPitch = renderView.rotationPitch;
                        info.nextState.entInstance.prevRotationYawHead = info.prevState.entInstance.prevRotationYawHead = renderView.prevRotationYawHead;
                        info.nextState.entInstance.rotationYawHead = info.prevState.entInstance.rotationYawHead = renderView.rotationYawHead;
                        renderTick = 1.0F;
                    }

                    info.prevModelInfo.forceRender(info.prevState.entInstance, 0.0D, -500.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);
                    info.nextModelInfo.forceRender(info.nextState.entInstance, 0.0D, -500.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);

                    info.prevState.entInstance.renderYawOffset = ff2;
                    info.prevState.entInstance.rotationYaw = ff3;
                    info.prevState.entInstance.rotationPitch = ff4;
                    info.prevState.entInstance.prevRotationYawHead = ff5;
                    info.prevState.entInstance.rotationYawHead = ff6;

                    info.nextState.entInstance.renderYawOffset = fff2;
                    info.nextState.entInstance.rotationYaw = fff3;
                    info.nextState.entInstance.rotationPitch = fff4;
                    info.nextState.entInstance.prevRotationYawHead = fff5;
                    info.nextState.entInstance.rotationYawHead = fff6;

                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                    Morph.proxy.tickHandlerClient.renderMorphInstance.setMainModel(info.interimModel);

                    Morph.proxy.tickHandlerClient.renderMorphInstance.doRender(event.entityPlayer, 0.0D, 0.0D - event.entityPlayer.yOffset, 0.0D, f1, renderTick);
                }

                //	        	event.entityPlayer.yOffset += Morph.proxy.tickHandlerClient.ySize;

                GL11.glPopMatrix();
                Morph.proxy.tickHandlerClient.renderingMorph = false;
            }
        }
        else
        {
            event.renderer.shadowSize = Morph.proxy.tickHandlerClient.playerRenderShadowSize;
        }
        Morph.proxy.tickHandlerClient.renderingPlayer--;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderSpecials(RenderLivingEvent.Specials.Pre event)
    {
        Iterator<Entry<String, MorphInfoClient>> ite = Morph.proxy.tickHandlerClient.playerMorphInfo.entrySet().iterator();
        while(ite.hasNext())
        {
            Entry<String, MorphInfoClient> e = ite.next();
            if(e.getValue().nextState.entInstance == event.entity || e.getValue().prevState != null && e.getValue().prevState.entInstance == event.entity)
            {
                if(e.getValue().prevState != null && e.getValue().prevState.entInstance instanceof EntityPlayer && !((EntityPlayer)e.getValue().prevState.entInstance).getCommandSenderName().equals(e.getKey()))
                {
                    event.setCanceled(true);
                }
                EntityPlayer player = event.entity.worldObj.getPlayerEntityByName(e.getKey());
                if(player != null && !(e.getValue().nextState.entInstance instanceof EntityPlayer && ((EntityPlayer)e.getValue().nextState.entInstance).getCommandSenderName().equals(e.getKey())))
                {
                    if(Morph.config.getSessionInt("showPlayerLabel") == 1)
                    {
                        if(e.getValue().nextState.entInstance instanceof EntityPlayer && !((EntityPlayer)e.getValue().nextState.entInstance).getCommandSenderName().equals(e.getKey()))
                        {
                            event.setCanceled(true);
                        }
                        RenderPlayer rend = (RenderPlayer)RenderManager.instance.getEntityRenderObject(player);

                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

                        if(Minecraft.isGuiEnabled() && player != Minecraft.getMinecraft().thePlayer && !player.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && player.riddenByEntity == null)
                        {
                            float f = 1.6F;
                            float f1 = 0.016666668F * f;
                            double d3 = player.getDistanceSqToEntity(Minecraft.getMinecraft().thePlayer);
                            float f2 = player.isSneaking() ? RendererLivingEntity.NAME_TAG_RANGE_SNEAK : RendererLivingEntity.NAME_TAG_RANGE;

                            if(d3 < (double)(f2 * f2))
                            {
                                String s = player.func_145748_c_().getFormattedText();
                                rend.func_96449_a(player, event.x, event.y, event.z, s, f1, d3);
                            }
                        }
                    }
                }
                break;
            }
        }
    }

}*/