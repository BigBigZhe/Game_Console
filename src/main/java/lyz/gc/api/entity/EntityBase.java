package lyz.gc.api.entity;

import lyz.gc.api.chess.Weapon;
import lyz.gc.items.EntityItem;
import lyz.gc.loader.ItemsLoader;
import lyz.gc.tileentity.TileEntityAttackBlock;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityBase extends EntityAnimal {

    private EntityPlayer player;//棋子拥有人
    private EntityItem entityItem;
    private boolean canPick;//是否可以拾起
    private int[] xy = new int[2];
    private Weapon[] weapons = new Weapon[3];//装备

    public EntityBase(World worldIn, EntityItem item) {
        super(worldIn);
        this.entityItem = item;
        this.canPick = true;
        for (Weapon weapon:weapons){
            weapon = Weapon.NONE;
        }
        this.setSize(0.7F, 0.7F);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand){
        if (!world.isRemote && canPick /* && player == this.player*/){//TODO
            ItemStack itemStack = player.getHeldItem(hand);
            if (itemStack.getItem() == ItemsLoader.STAFF){
                player.setHeldItem(hand, new ItemStack(entityItem.setOwnPlayer(player, this.xy)));
                world.removeEntity(this);
                TileEntity entity = world.getTileEntity(this.getPosition().offset(EnumFacing.DOWN));
                if (entity instanceof TileEntityAttackBlock){
                    ((TileEntityAttackBlock) entity).setHasChess(false);
                }
            }
            if (itemStack.getItem() == entityItem &&  player == entityItem.getOwnPlayer()){
                player.setHeldItem(hand, new ItemStack(ItemsLoader.STAFF));
                TileEntity entity = world.getTileEntity(this.getPosition().offset(EnumFacing.DOWN));
                if (entity instanceof TileEntityAttackBlock){
                    ((TileEntityAttackBlock) entity).setHasChess(true);
                }
            }
        }
        return false;
    }

    @Override
    public boolean canBePushed() { return false; }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {//左击
        if (!this.world.isRemote && !this.isDead) {
            if (DamageSource.OUT_OF_WORLD.equals(source)) {
                this.setDead();
            }else {
                System.out.println(this.weapons[0]);
            }
            return false;
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////
    public boolean isCanPick() {
        return canPick;
    }

    public void setCanPick(boolean canPick) {
        this.canPick = canPick;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    public int[] getXy() {
        return xy;
    }

    public void setXy(int[] xy) {
        this.xy = xy;
    }

    public void setWeapon(Weapon weapon, int dex) { this.weapons[dex] = weapon; }

    //////////////////////////////////////////////////////////////////////
}
