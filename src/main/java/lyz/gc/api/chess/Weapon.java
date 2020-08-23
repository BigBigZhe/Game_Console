package lyz.gc.api.chess;

public enum Weapon {
    NONE("none", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
    SWORD("Sword", 100, 0, 0, 0,
            0, 0, 0, 0, 0, 0)
    ;

    private final String name;
    ///////////攻击力/////法强//////////攻速//////生命值////移速/////////攻击距离/////////暴击几率////////暴击伤害/////护甲////////魔抗/////
    public int damage, magicDamage, attackSpeed, health, walkSpeed, attackDistance, criticalChance, criticalDamage, armor, spellResistance;

    Weapon(String name, int damage, int magicDamage, int attackSpeed, int health, int walkSpeed,
           int attackDistance, int criticalChance, int criticalDamage, int armor, int spellResistance) {
        this.name = name;
        this.damage = damage;
        this.magicDamage = magicDamage;
        this.attackSpeed = attackSpeed;
        this.health = health;
        this.walkSpeed = walkSpeed;
        this.attackDistance = attackDistance;
        this.criticalChance = criticalChance;
        this.criticalDamage = criticalDamage;
        this.armor = armor;
        this.spellResistance = spellResistance;
    }

    public String toString()
    {
        return this.getName();
    }

    public String getName()
    {
        return this.name;
    }
}
