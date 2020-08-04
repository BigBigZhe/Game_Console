package lyz.gc.blocks.functionblock;

import lyz.gc.api.block.BlockBase;
import lyz.gc.loader.BlocksLoader;
import lyz.gc.loader.ItemsLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AlternativeBlock extends BlockBase {

    public AlternativeBlock(String name) {
        super(name);
        ItemsLoader.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
        BlocksLoader.BLOCKS.add(this);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                         EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return true;
    }
}
