package fr.qilat.prisonrp.server.game.drop;

import fr.qilat.prisonrp.server.utils.Location;
import fr.qilat.prisonrp.server.utils.UtilItem;
import fr.qilat.prisonrp.server.utils.Utils;
import fr.qilat.prisonrp.server.utils.task.Task;
import fr.qilat.prisonrp.server.utils.task.TaskManager;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

/**
 * Created by Qilat on 21/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
@SideOnly(Side.SERVER)
public class Drop {

    private static final int LOOT_RATE = 10;
    private static Map<ItemStack, Integer> lootMap = new HashMap<ItemStack, Integer>();
    private static List<ItemStack> loots = new ArrayList<ItemStack>();
    private static List<Drop> drops = new ArrayList<Drop>();
    private static Random random = new Random();

    static {
        lootMap.put(new UtilItem(Items.COOKED_CHICKEN, 1, 0).setName("La bonne conserve").getItemStack(), 50);
        lootMap.put(new UtilItem(Blocks.CARPET, 1, 0).setName("Beretta M9").getItemStack(), 10);
        lootMap.put(new UtilItem(Items.MELON_SEEDS, 1, 0).setName("Balle légère").getItemStack(), 25);

        for (Map.Entry<ItemStack, Integer> entry : lootMap.entrySet())
            for (int i = 0; i < entry.getValue(); i++)
                loots.add(entry.getKey().copy());
    }

    private Location location;
    private Location baseLoc = null;
    private Location[] dropLoc = new Location[3];
    private BlockChest[] chest = new BlockChest[3];
    private int task;

    private EntityArmorStand asParachute, asChest1, asChest2, asChest3;

    public Drop(Location loc) {
        final int baseY = 200;
        loc.setY(baseY);
        this.location = loc.clone();
        Utils.broadcastMessage("§6§lANNONCE §e>> §7Un largage va apparaitre en §aX : " + location.getBlockX() + " Z : " + location.getBlockZ());

        this.initAS(loc, baseY);

        drops.add(this);

        task = new Task(Side.SERVER) {
            @Override
            public void run() {
                if (!asChest3.getEntityWorld().isAirBlock(asChest3.getPosition().add(0, -1, 0))
                        || !asChest3.getEntityWorld().isAirBlock(asChest3.getPosition().add(0, -2, 0))) {

                    Drop.this.location = new Location(asChest3.getEntityWorld(), asChest3.getPosition());

                    dropLoc[0] = Location.highestBlockAtLocation(new Location(asChest3.getEntityWorld(), asChest3.getPosition()));

                    dropLoc[1] = dropLoc[0].clone();
                    dropLoc[2] = dropLoc[0].clone();

                    dropLoc[0].setY(dropLoc[0].getY() + 1);
                    dropLoc[1].setY(dropLoc[0].getY() + 1);
                    dropLoc[2].setY(dropLoc[0].getY() + 2);

                    dropLoc[0].getWorld().setBlockState(dropLoc[0].getBlockPos(), Blocks.CHEST.getDefaultState());
                    chest[0] = (BlockChest) dropLoc[0].getBlock();
                    setChestInv(dropLoc[0]);


                    dropLoc[1].getWorld().setBlockState(dropLoc[1].getBlockPos(), Blocks.CHEST.getDefaultState());
                    chest[1] = (BlockChest) dropLoc[1].getBlock();
                    setChestInv(dropLoc[1]);

                    dropLoc[2].getWorld().setBlockState(dropLoc[2].getBlockPos(), Blocks.CHEST.getDefaultState());
                    chest[2] = (BlockChest) dropLoc[2].getBlock();
                    setChestInv(dropLoc[2]);

                    cancelTask();
                    return;
                } else {
                    if (baseLoc == null) {
                        baseLoc = location.clone();
                        baseLoc.setY(baseY);
                    }
                    double speed = 5.5D;
                    double move = speed / 5;
                    moveAS(-1 * move);
                    return;
                }
            }
        }.runTaskTimer(4, 0);
    }

    public static void despawnAll() {
        for (Drop drop : drops) {
            drop.despawn();
        }
    }

    private void initAS(Location loc, int baseY) {
        loc.setY(baseY + 1);
        asParachute = new EntityArmorStand(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        loc.getWorld().spawnEntity(asParachute);
        setupAS(asParachute, ASType.PARACHUTE);

        loc.setY(baseY);
        asChest1 = new EntityArmorStand(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        loc.getWorld().spawnEntity(asChest1);
        setupAS(asChest1, ASType.CHEST);

        loc.setY(baseY + 1);
        asChest2 = new EntityArmorStand(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        loc.getWorld().spawnEntity(asChest2);
        setupAS(asChest2, ASType.CHEST);

        loc.setY(baseY + 2);
        asChest3 = new EntityArmorStand(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        loc.getWorld().spawnEntity(asChest3);
        setupAS(asChest3, ASType.CHEST);
    }

    private void setupAS(EntityArmorStand armorStand, ASType type) {
        NBTTagCompound armornbt = new NBTTagCompound();
        armorStand.writeEntityToNBT(armornbt);
        armornbt.setBoolean("Invulnerable", true);
        armorStand.readEntityFromNBT(armornbt);

        armorStand.setInvisible(true);
        armorStand.setNoGravity(true);
        switch (type) {
            case CHEST:
                armorStand.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Blocks.CHEST));
                break;
            case PARACHUTE:
                armorStand.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Blocks.YELLOW_FLOWER));
                break;
        }

    }

    private void moveAS(double delta) {
        asParachute.move(0, delta, 0);
        asChest1.move(0, delta, 0);
        asChest2.move(0, delta, 0);
        asChest3.move(0, delta, 0);
    }

    private void despawnAS() {
        if (asChest1 != null) asChest1.setDead();
        if (asChest2 != null) asChest2.setDead();
        if (asChest3 != null) asChest3.setDead();
        if (asParachute != null) asParachute.setDead();
    }

    private void cancelTask() {
        TaskManager.cancelTask(task);
        this.despawnAS();

        new Task(Side.SERVER) {
            @Override
            public void run() {
                Drop.this.despawn();
            }
        }.runTaskLater(10 * 60 * 20L);
    }

    public void despawn() {
        location.getWorld().setBlockToAir(location.getBlockPos());
        location.setY(location.getY() + 1);
        location.getWorld().setBlockToAir(location.getBlockPos());
        location.setY(location.getY() + 1);
        location.getWorld().setBlockToAir(location.getBlockPos());
        drops.remove(this);
    }

    private void setChestInv(Location loc) {
        if (loc.getWorld().getTileEntity(loc.getBlockPos()) instanceof TileEntityChest) {
            TileEntityChest tileEntityChest = (TileEntityChest) loc.getWorld().getTileEntity(loc.getBlockPos());
            for (int i = 0; i < 26; i++) {
                if (random.nextInt(LOOT_RATE * 3) < LOOT_RATE) {
                    tileEntityChest.setInventorySlotContents(i, loots.get(random.nextInt(loots.size())).copy());
                }
            }
        }
    }

    private enum ASType {
        CHEST,
        PARACHUTE
    }
}