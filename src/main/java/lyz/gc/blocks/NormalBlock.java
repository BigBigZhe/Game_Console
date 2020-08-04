package lyz.gc.blocks;

import lyz.gc.api.block.BlockBase;
import lyz.gc.loader.BlocksLoader;
import lyz.gc.loader.ItemsLoader;
import net.minecraft.item.ItemBlock;

public class NormalBlock extends BlockBase {
    public NormalBlock(String name) {
        super(name);
        ItemsLoader.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        BlocksLoader.BLOCKS.add(this);
    }
}
