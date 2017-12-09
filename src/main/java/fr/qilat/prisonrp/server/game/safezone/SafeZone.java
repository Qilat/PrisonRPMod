package fr.qilat.prisonrp.server.game.safezone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Qilat on 29/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class SafeZone {
    private int id;
    private String name;
    private int pos1X;
    private int pos1Y;
    private int pos1Z;
    private int pos2X;
    private int pos2Y;
    private int pos2Z;

    public SafeZone() {
    }

    public SafeZone(BlockPos pos1, BlockPos pos2) {
        this(null, pos1, pos2);
    }
    public SafeZone(String name, BlockPos pos1, BlockPos pos2) {
        this.id = SafeZoneManager.currentId++;
        this.name = name;
        this.setPos1(pos1);
        this.setPos2(pos2);
    }

    public boolean contains(BlockPos pos) {
        int posX = pos.getX();
        int posZ = pos.getZ();
        return ((posX <= pos1X && posX >= pos2X)
                || (posX >= pos1X && posX <= pos2X))
                && ((posZ <= pos1Z && posZ >= pos2Z)
                || (posZ >= pos1Z && posZ <= pos2Z));
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPos1(BlockPos pos1) {
        this.pos1X = pos1.getX();
        this.pos1Y = pos1.getY();
        this.pos1Z = pos1.getZ();
    }

    public void setPos2(BlockPos pos2) {
        this.pos2X = pos2.getX();
        this.pos2Y = pos2.getY();
        this.pos2Z = pos2.getZ();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos1X() {
        return pos1X;
    }

    public void setPos1X(int pos1X) {
        this.pos1X = pos1X;
    }

    public int getPos1Y() {
        return pos1Y;
    }

    public void setPos1Y(int pos1Y) {
        this.pos1Y = pos1Y;
    }

    public int getPos1Z() {
        return pos1Z;
    }

    public void setPos1Z(int pos1Z) {
        this.pos1Z = pos1Z;
    }

    public int getPos2X() {
        return pos2X;
    }

    public void setPos2X(int pos2X) {
        this.pos2X = pos2X;
    }

    public int getPos2Y() {
        return pos2Y;
    }

    public void setPos2Y(int pos2Y) {
        this.pos2Y = pos2Y;
    }

    public int getPos2Z() {
        return pos2Z;
    }

    public void setPos2Z(int pos2Z) {
        this.pos2Z = pos2Z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
