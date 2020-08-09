package lyz.gc.api.chess;

public enum ChessBase {
    Base("base", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    ;

    private final String name;
    private int health, walkSpeed, attackSpeed, damage, magicDamage, attackDistance, criticalChance, criticalDamage, armor, spellResistance;

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

    public int getHealth() {
        return health;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public int getAttackPower() {
        return damage;
    }

    public int getMagicPower() {
        return magicDamage;
    }

    public int getAttackDistance() {
        return attackDistance;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public int getCriticalDamage() {
        return criticalDamage;
    }

    public int getArmor() {
        return armor;
    }

    public int getSpellResistance() {
        return spellResistance;
    }
}
