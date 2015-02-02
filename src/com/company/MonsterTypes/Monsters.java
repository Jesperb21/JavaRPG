package com.company.MonsterTypes;

public enum Monsters {
    Mage, SkeletonArcher, Slime, Zombie;

    public static Monsters get(int i) {
        return values()[i];
    }
}
