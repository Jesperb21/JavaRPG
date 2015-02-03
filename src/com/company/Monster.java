package com.company;

public abstract class Monster  extends Character{
    @Override
    public void Die() {
    }

    public abstract void failAttack();
}
enum Monsters {
    Mage, SkeletonArcher, Slime, Zombie;

    public static Monsters get(int i) {
        return values()[i];
    }
}

