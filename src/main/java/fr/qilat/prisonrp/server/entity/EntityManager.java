package fr.qilat.prisonrp.server.entity;

import fr.qilat.prisonrp.PrisonRPCore;
import fr.qilat.prisonrp.client.render.entity.RenderCZombie;
import fr.qilat.prisonrp.server.utils.Logger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;


/**
 * Created by Qilat on 25/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class EntityManager {

    public static void registerEntities() {
        Logger.info("EntitiesLivingHandler: registerEntities");
        int i = 0;
        createEntity(EntityCZombie.class, EntityCZombie.name, new Color(0, 255, 0).getRGB(), new Color(255, 0, 0).getRGB(), i++);
        EntityRegistry.addSpawn(EntityCZombie.class, 10, 0, 10, EnumCreatureType.MONSTER);
        EntityRegistry.addSpawn(EntityCZombie.class, 10, 34, 45, EnumCreatureType.MONSTER);
    }

    private static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor, int id) {
        Logger.info("EntitiesLivingHandler: createEntity : " + entityName);
        EntityRegistry.registerModEntity(entityClass, entityName, id, PrisonRPCore.instance, 64, 1, true, solidColor, spotColor);
    }

    @SideOnly(Side.CLIENT)
    public static void registerAllEntityModels() {
        Logger.info("EntitiesLivingHandler: registerAllModels");
        registerMobModels();
    }

    @SideOnly(Side.CLIENT)
    private static void registerMobModels() {
        Logger.info("EntitiesLivingHandler: registerMobModels");
        registerMobModel(EntityCZombie.class, RenderCZombie.RenderFactory.INSTANCE);
    }

    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerMobModel(Class<T> entity, IRenderFactory<? super T> renderFactory) {
        Logger.info("EntitiesLivingHandler: registerMobModel : " + entity.getName());
        RenderingRegistry.registerEntityRenderingHandler(entity, renderFactory);
    }


}
