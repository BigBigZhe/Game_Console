package lyz.gc.items;

import lyz.gc.api.item.ItemBase;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.loader.NetWorkLoader;
import lyz.gc.network.MessageSpawnEntity;
import lyz.gc.tileentity.TileEntityAttackBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Staff extends ItemBase {
    public Staff(String name) {
        super(name);
        ItemsLoader.ITEMS.add(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityAttackBlock){
                TileEntityAttackBlock t = (TileEntityAttackBlock)tileEntity;
                if (t.getPlayer() == player){

                }
                t.setPlayer(player);
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
