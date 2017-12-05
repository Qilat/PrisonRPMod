package fr.qilat.prisonrp.server.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Qilat on 21/11/2017 for forge-1.10.2-12.18.3.2511-mdk.
 */
public class UtilItem {

    private ItemStack is;

    public UtilItem() {
    }

    public UtilItem(Block block, int amount, int metadata) {
        this.is = new ItemStack(block, amount, metadata);
    }

    public UtilItem(Item item, int amount, int metadata) {
        this.is = new ItemStack(item, amount, metadata);
    }

    public UtilItem(Item item, int metadata) {
        this(item, 0, metadata);
    }

    public ItemStack getItemStack() {
        return is;
    }

    public UtilItem setName(String name) {
        this.is.setStackDisplayName(name);
        return this;
    }

    public UtilItem setAmount(int amount) {
        this.is = new ItemStack(is.getItem(), amount, is.getMetadata());
        return this;
    }

    public UtilItem createItem(Item item, int amount, int metadata) {
        this.is = new ItemStack(item, amount, metadata);
        return this;
    }

    public UtilItem createItem(Block block, int amount, int metadata) {
        this.is = new ItemStack(block, amount, metadata);
        return this;
    }

}
