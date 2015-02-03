package com.company.MonsterTypes;

public enum Monsters {
    Zombie;

    public static Monsters get(int i) {
        return values()[i];
    }
}
