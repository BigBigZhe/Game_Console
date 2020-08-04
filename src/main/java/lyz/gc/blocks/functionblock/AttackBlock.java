package lyz.gc.blocks.functionblock;

import lyz.gc.api.block.BlockBase;
import lyz.gc.loader.BlocksLoader;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.loader.NetWorkLoader;
import lyz.gc.network.MessageSpawnEntity;
import lyz.gc.tileentity.TileEntityAttackBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AttackBlock extends BlockBase {
    public AttackBlock(String name) {
        super(name);
        ItemsLoader.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        BlocksLoader.BLOCKS.add(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityAttackBlock){
                TileEntityAttackBlock t = (TileEntityAttackBlock)tileEntity;
                if (t.getPlayer() == playerIn){
                    Item i = playerIn.getHeldItem(hand).getItem();
                    if (i == ItemsLoader.DEBUG_STAFF){
                        System.out.println("hasChess:" + t.isHasChess());
                        System.out.println("canMove:" + t.isCanMove());
                    }else if (i == ItemsLoader.ZF1 && !t.isHasChess()){
                        t.setHasChess(true);
                        MessageSpawnEntity message = new MessageSpawnEntity(pos, 0);
                        NetWorkLoader.instance.sendToServer(message);
                        playerIn.setHeldItem(hand, new ItemStack(ItemsLoader.STAFF));
                    }
                }
                t.setPlayer(playerIn);
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
        return new TileEntityAttackBlock();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
}
