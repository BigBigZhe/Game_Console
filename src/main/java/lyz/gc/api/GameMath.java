package lyz.gc.api;

import lyz.gc.api.chess.Weapon;

import java.util.Random;

public class GameMath {
    //随机装备
    public Weapon randomWeapon(){
        int num = new Random().nextInt(2);
        if (num == 0){
            return Weapon.NONE;
        }else if (num == 1){
            return Weapon.SWORD;
        }
        return Weapon.NONE;
    }

}
