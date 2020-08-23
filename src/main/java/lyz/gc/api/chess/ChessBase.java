package lyz.gc.api.chess;

public enum ChessBase {
    Base("base", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    ;

    private final String name;
    public int health, walkSpeed, attackSpeed, damage, magicDamage, attackDistance, criticalChance, criticalDamage, armor, spellResistance;

    ChessBase(String name, int health, int walkSpeed, int attackSpeed, int damage,
              int magicDamage, int attackDistance, int criticalChance, int criticalDamage, int armor, int spellResistance) {
        this.name = name;
        this.health = health;
        this.walkSpeed = walkSpeed;
        this.attackSpeed = attackSpeed;
        this.damage = damage;
        this.magicDamage = magicDamage;
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
