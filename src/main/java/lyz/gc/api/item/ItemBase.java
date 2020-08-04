package lyz.gc.api.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name){
        setRegistryName(name);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

}
