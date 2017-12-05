package fr.qilat.prisonrp.server.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Qilat on 25/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class EntityCZombie extends EntityZombie {
    public static final String name = "czombie";
    private GrowthType growthType = GrowthType.KID;

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
        if (this.world.isDaytime() && !this.world.isRemote && !this.isChild() && (this.getZombieType() == null || this.getZombieType().isSunSensitive())) {
            float f = this.getBrightness(1.0F);
            BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, (double) Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, (double) Math.round(this.posY), this.posZ);

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(blockpos)) {
                boolean flag = true;
                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (itemstack != null) {
                    if (itemstack.isItemStackDamageable()) {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                            this.renderBrokenItemStack(itemstack);
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, (ItemStack) null);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.extinguish();
                }
            }
        }
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
