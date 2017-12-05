package fr.qilat.prisonrp.server.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;

/**
 * Created by Qilat on 16/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class CustomISound implements ISound {

    ResourceLocation location;

    public CustomISound(ResourceLocation location) {
        this.location = location;
    }


    @Override
    public ResourceLocation getSoundLocation() {
        return location;
    }

    @Nullable
    @Override
    public SoundEventAccessor createAccessor(SoundHandler handler) {
        return null;
    }

    @Override
    public Sound getSound() {
        return null;
    }

    @Override
    public SoundCategory getCategory() {
        return null;
    }

    @Override
    public boolean canRepeat() {
        return false;
    }

    @Override
    public int getRepeatDelay() {
        return 0;
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public float getPitch() {
        return 0;
    }

    @Override
    public float getXPosF() {
        return 0;
    }

    @Override
    public float getYPosF() {
        return 0;
    }

    @Override
    public float getZPosF() {
        return 0;
    }

    @Override
    public AttenuationType getAttenuationType() {
        return null;
    }
}
