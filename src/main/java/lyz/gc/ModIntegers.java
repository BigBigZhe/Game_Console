package lyz.gc;

public class ModIntegers {
    //Varieties
    public static final int V_VILLAGER = 115;//村民
    public static final int V_CHICKEN = 116; //鸡
    public static final int V_COW = 117;     //牛
    public static final int V_HORSE = 118;   //马
    public static final int V_SHEEP = 119;   //羊
    public static final int V_PIG = 120;     //猪
    public static final int V_WOLF = 121;    //狼

    public static final int V_ENDERMAN = 145;//末影人
    public static final int V_ZOMBIE = 146;  //僵尸
    public static final int V_CREEPER = 147; //苦力怕
    public static final int V_SKELETON = 148;//骷髅
    public static final int V_SLIME = 149;   //史莱姆
    public static final int V_SPIDER = 150;  //蜘蛛
    public static final int V_WITCH = 151;   //女巫

    //Occupation
    public static final int O_GOLD = 15;    //金
    public static final int O_WOOD = 16;    //木
    public static final int O_WATER = 17;   //水
    public static final int O_FIRE = 18;    //火
    public static final int O_EARTH = 19;   //土
    public static final int O_WIZARD = 20;  //魔法师
    public static final int O_TECH = 21;    //科学家
    public static final int O_SUMMON = 22;  //召唤师
    public static final int O_ASSASSIN = 23;//刺客

    //Switch                            V_O = {V, O}
    public static final int[] VILLAGER_TECH = {V_VILLAGER, O_TECH};
    public static final int[] CHICKEN_GOLD = {V_CHICKEN, O_GOLD};
    public static final int[] COW_EARTH = {V_COW, O_EARTH};
    public static final int[] SHEEP_WOOD = {V_SHEEP, O_WOOD};
    public static final int[] CREEPER_FIRE = {V_CREEPER, O_FIRE};
    public static final int[] ENDERMAN_WIZARD = {V_ENDERMAN, O_WIZARD};
    public static final int[] ZOMBIE_SUMMON = {V_ZOMBIE, O_SUMMON};
}
