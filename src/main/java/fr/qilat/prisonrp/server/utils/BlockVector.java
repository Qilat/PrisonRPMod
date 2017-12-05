package fr.qilat.prisonrp.server.utils;

import java.util.Map;

/**
 * Created by Qilat on 03/12/2017 for forge-1.10.2-12.18.3.2511-mdk.
 * Copyright Bukkit
 */
public class BlockVector extends Vector {
    public BlockVector() {
        this.x = 0.0D;
        this.y = 0.0D;
        this.z = 0.0D;
    }

    public BlockVector(Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }

    public BlockVector(int x, int y, int z) {
        this.x = (double) x;
        this.y = (double) y;
        this.z = (double) z;
    }

    public BlockVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockVector(float x, float y, float z) {
        this.x = (double) x;
        this.y = (double) y;
        this.z = (double) z;
    }

    public static BlockVector deserialize(Map<String, Object> args) {
        double x = 0.0D;
        double y = 0.0D;
        double z = 0.0D;
        if (args.containsKey("x")) {
            x = ((Double) args.get("x")).doubleValue();
        }

        if (args.containsKey("y")) {
            y = ((Double) args.get("y")).doubleValue();
        }

        if (args.containsKey("z")) {
            z = ((Double) args.get("z")).doubleValue();
        }

        return new BlockVector(x, y, z);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BlockVector)) {
            return false;
        } else {
            BlockVector other = (BlockVector) obj;
            return (int) other.getX() == (int) this.x && (int) other.getY() == (int) this.y && (int) other.getZ() == (int) this.z;
        }
    }

    public BlockVector clone() {
        return (BlockVector) super.clone();
    }
}

