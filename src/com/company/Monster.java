package com.company;

public abstract class Monster  extends Character{
    @Override
    public int Die(Character Defeater) {
        int DifLvl = Level - Defeater.Level;
        int exp = 10 + DifLvl;
        if (exp < 1) exp = 1;
        return exp;
    }

    public abstract void failAttack();
}
enum Monsters {
    Zombie;

    public static Monsters get(int i) {
        return values()[i];
    }
}

