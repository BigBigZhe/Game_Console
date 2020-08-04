package lyz.gc.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityAttackBlock extends TileEntity {

    private EntityPlayer player;//棋子拥有人
    private BlockPos corePos;//-9 12 7  20 4 5
    private boolean hasChess;//是否有棋子
    private boolean canMove;

    public TileEntityAttackBlock(){
        this.corePos = new BlockPos(20, 4, 5);
        this.hasChess = false;
        this.canMove = true;
    }

    //////////////////////////////////////////////////////////////////////
    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public BlockPos getCorePos() {
        return corePos;
    }

    public void setCorePos(BlockPos corePos) {
        this.corePos = corePos;
    }

    public boolean isHasChess() {
        return hasChess;
    }

    public void setHasChess(boolean hasChess) { this.hasChess = hasChess; }

    public boolean isCanMove() { return canMove; }

    public void setCanMove(boolean canMove) { this.canMove = canMove; }
    //////////////////////////////////////////////////////////////////////
}
