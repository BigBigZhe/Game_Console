package lyz.gc.api.chess;

public class PlayerInfo {
    public int health, level, exp, expLimit, money, streak;//streak +连胜 -连败

    public PlayerInfo(){ initInfo(); }

    private void initInfo(){
        this.health = 100;
        this.level = 1;
        this.exp = 0;
        this.expLimit = 2;
        this.money = 0;
        this.streak = 0;
    }

}
