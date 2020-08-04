package lyz.gc.api.chess;

public enum ChessBase {
    Base("base", 0, 0, 0, 0, 0, 0);

    private String name;
    private int health, walkSpeed, attackSpeed, attackPower, magicPower, attackDistance;

    ChessBase(String name, int health, int walkSpeed, int attackSpeed, int attackPower,
              int magicPower, int attackDistance) {
        this.name = name;
        this.health = health;
        this.walkSpeed = walkSpeed;
        this.attackSpeed = attackSpeed;
        this.attackPower = attackPower;
        this.magicPower = magicPower;
        this.attackDistance = attackDistance;
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
        return attackPower;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public int getAttackDistance() {
        return attackDistance;
    }
}
