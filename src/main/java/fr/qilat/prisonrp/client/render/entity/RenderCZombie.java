package fr.qilat.prisonrp.client.render.entity;

import fr.qilat.prisonrp.server.entity.EntityCZombie;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * Created by Qilat on 25/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class RenderCZombie extends RenderZombie {

    public RenderCZombie(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    public static class RenderFactory implements IRenderFactory<EntityCZombie> {
        public static final RenderFactory INSTANCE = new RenderFactory();

        @Override
        public Render<? super EntityCZombie> createRenderFor(RenderManager manager) {
            return new RenderCZombie(Minecraft.getMinecraft().getRenderManager());
        }
    }

}

