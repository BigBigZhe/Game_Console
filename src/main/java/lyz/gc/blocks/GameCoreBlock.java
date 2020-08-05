package lyz.gc.blocks;

import lyz.gc.api.block.BlockBase;
import lyz.gc.loader.BlocksLoader;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.tileentity.TileEntityGameCore;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GameCoreBlock extends BlockBase {
    public GameCoreBlock(String name) {
        super(name);
        ItemsLoader.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        BlocksLoader.BLOCKS.add(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof TileEntityGameCore){
                TileEntityGameCore core = (TileEntityGameCore)entity;
                //core.addName(playerIn.getUniqueID());//加入游戏
                if (/*core.getPlayerNum() == 8 &&*/ !core.isBegin()){
                    core.begin();
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityGameCore();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
