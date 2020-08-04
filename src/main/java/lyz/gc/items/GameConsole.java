package lyz.gc.items;

import lyz.gc.api.item.ItemBase;
import lyz.gc.loader.ItemsLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GameConsole extends ItemBase {

    public GameConsole(String name){
        super(name);
        ItemsLoader.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos,
         EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            worldIn.setBlockToAir(new BlockPos(hitX, hitY, hitZ));
        }
        return EnumActionResult.SUCCESS;
    }
}
