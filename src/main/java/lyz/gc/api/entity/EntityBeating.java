package lyz.gc.api.entity;

import lyz.gc.api.chess.ChessBase;
import lyz.gc.api.chess.Weapon;
import net.minecraft.entity.EntityLiving;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityBeating extends EntityLiving {

    public static final DataParameter<Boolean> AFIRE = EntityDataManager.createKey(EntityBeating.class, DataSerializers.BOOLEAN);
    public ChessBase chessBase;
    public Weapon[] weapon;
    public int variety, occupation, ownNum, enemyNum;

    public EntityBeating(World worldIn, int[] chessSwitch, int ownNum, int enemyNum, ChessBase chessBase, Weapon[] weapon) {
        super(worldIn);
        this.variety = chessSwitch[0];
        this.occupation = chessSwitch[1];
        this.ownNum = ownNum;
        this.enemyNum = enemyNum;
        this.chessBase = chessBase;
        this.weapon = weapon;
        addProperties();
    }

    @Override
    protected void entityInit(){
        super.entityInit();
        this.dataManager.register(AFIRE, Boolean.FALSE);
    }
    //添加装备提供的属性
    protected void addProperties(){
        for (Weapon w:weapon) {
            chessBase.health += w.health;
            chessBase.magicDamage += w.magicDamage;
            chessBase.attackSpeed += w.attackSpeed;
            chessBase.walkSpeed += w.walkSpeed;
            chessBase.attackDistance += w.attackDistance;
            chessBase.criticalChance += w.criticalChance;
            chessBase.criticalDamage += w.criticalDamage;
            chessBase.armor += w.armor;
            chessBase.spellResistance += w.spellResistance;
        }
    }
}
