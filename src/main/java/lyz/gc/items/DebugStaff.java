package lyz.gc.items;

import lyz.gc.api.item.ItemBase;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.tileentity.TileEntityAttackBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugStaff extends ItemBase {
    public DebugStaff(String name) {
        super(name);
        ItemsLoader.ITEMS.add(this);
    }
}
