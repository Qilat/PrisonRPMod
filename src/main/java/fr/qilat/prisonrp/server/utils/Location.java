package fr.qilat.prisonrp.server.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Qilat on 19/11/2017.
 */
public class Location {

    private World world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public Location(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(World world, double x, double y, double z) {
        this(world, x, y, z, 0f, 0f);
    }

    public Location(Entity entity) {
        this(entity.world, entity.posX, entity.posY, entity.posZ);
    }

    public Location(World world, BlockPos blockPos) {
        this(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static Location highestBlockAtLocation(Location loc) {
        Location returnLoc = loc.clone();
        returnLoc.setY(loc.getWorld().getHeight());

        while (returnLoc.getWorld().isAirBlock(returnLoc.getBlockPos())) {
            returnLoc.setY(returnLoc.getY() - 1);
        }

        return returnLoc;
    }

    public int getBlockX() {
        return (int) x;
    }

    public int getBlockY() {
        return (int) y;
    }

    public int getBlockZ() {
        return (int) z;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Block getBlock() {
        return world.getBlockState(new BlockPos(getBlockX(), getBlockY(), getBlockZ())).getBlock();
    }

    public BlockPos getBlockPos() {
        return new BlockPos(this.x, this.y, this.z);
    }

    @Override
    public Location clone() {
        return new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    //Copyright Bukkit
    public Location add(Location vec) {
        if (vec != null && vec.getWorld() == this.getWorld()) {
            this.x += vec.x;
            this.y += vec.y;
            this.z += vec.z;
            return this;
        } else {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }
    }

    //Copyright Bukkit
    public Location add(Vector vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }
}
