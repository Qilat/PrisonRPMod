package fr.qilat.prisonrp.server.item;

import fr.qilat.prisonrp.PrisonRPItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by Qilat on 19/11/2017 for PoudlardForge.
 */
public class Wand extends Item {
    public static final String NAME = "wand";

    public Wand() {
        super();
        PrisonRPItems.setItemName(this, NAME);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            player.sendMessage(new TextComponentString("bonjour " + player.getName()));
        }
        return new ActionResult(EnumActionResult.PASS, player.getHeldItem(hand));
    }

}
