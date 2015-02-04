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

    public void LvlUp() {
        Level += 1;
        //Health
        /*(L * 100 + D) * ?>0(1. 1I)
        */
        Maxhealth = (int)Math.round(((Level * 100) + DefensePower) * (1+ (0.1 * Intelligence)));
        Heal(100);
        int response = Console.RandomInt(1,4);
        switch (response){
            case 1:
                Strength++;
                break;
            case 2:
                DefensePower++;
                break;
            case 3:
                Intelligence++;
                break;
            case 4:
                Agility++;
                break;
        }
    }
}
enum Monsters {
    Mage,SkeletonArcher,Slime,Zombie;

    public static Monsters get(int i) {
        return values()[i];
    }
}

