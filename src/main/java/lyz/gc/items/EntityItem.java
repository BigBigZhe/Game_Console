package lyz.gc.items;

import lyz.gc.api.item.ItemBase;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.loader.NetWorkLoader;
import lyz.gc.network.MessageSpawnEntity;
import lyz.gc.tileentity.TileEntityAttackBlock;
import lyz.gc.tileentity.TileEntityGameCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityItem extends ItemBase {

    private EntityPlayer ownPlayer;//棋子拥有人
    private int[] nxy = new int[3];//num x y
    private int type;

    public EntityItem(String name) {
        super(name);
        ItemsLoader.ITEMS.add(this);
    }

    public EntityItem setOwnPlayer(EntityPlayer ownPlayer, int[] xy) {
        this.ownPlayer = ownPlayer;
        World world = ownPlayer.world;
        TileEntity tileEntity = world.getTileEntity(new BlockPos(20, 4, 5));
        if (tileEntity instanceof TileEntityGameCore){
            TileEntityGameCore tileEntityGameCore = (TileEntityGameCore)tileEntity;
            nxy[0] = tileEntityGameCore.getPlayerNumber(ownPlayer);
            nxy[1] = xy[0];
            nxy[2] = xy[1];
        }
        return this;
    }

    //////////////////////////////////////////////////////////////////////
    public EntityPlayer getOwnPlayer() { return ownPlayer; }

    public void setPos(int[] nxy) { this.nxy = nxy; }

    public int[] getPos() { return nxy; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }
}
