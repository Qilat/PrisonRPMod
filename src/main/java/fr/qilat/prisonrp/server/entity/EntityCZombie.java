package fr.qilat.prisonrp.server.entity;

import fr.qilat.prisonrp.CustomLootTable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Qilat on 25/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class EntityCZombie extends EntityZombie {
    public static final String name = "czombie";
    private GrowthType growthType = GrowthType.WALKER;

    public EntityCZombie(World worldIn) {
        super(worldIn);
        this.applyCustomEntityAttributes();
    }


    public EntityCZombie(World worldIn, GrowthType type) {
        super(worldIn);
        this.growthType = type;
        this.applyCustomEntityAttributes();
        this.setSize();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.extinguish();
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player) {
        return 0;
    }


    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return CustomLootTable.CZOMBIE;
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    private void applyCustomEntityAttributes() {
        switch (this.growthType) {
            case WALKER:
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
                this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6D);
                this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
                this.setAIMoveSpeed(1.0F);
                break;
            case KID:
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
                this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D);
                this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8D);
                this.setAIMoveSpeed(0.8F);
                break;
        }

        for (EntityAITasks.EntityAITaskEntry entry : this.tasks.taskEntries) {
            if (entry.action instanceof EntityAIWatchClosest) {
                this.tasks.removeTask(entry.action);
                this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
                break;
            }
        }
    }

    public void setSize() {
        switch (this.growthType) {
            case WALKER:
                this.setChild(false);
                break;
            case KID:
                this.setChild(true);
                break;
        }
    }

    public GrowthType getGrowthType() {
        return growthType;
    }

    enum GrowthType {
        WALKER,
        KID
    }
}
