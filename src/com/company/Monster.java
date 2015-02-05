package com.company;


public abstract class Monster  extends Character{
    public boolean isBoss = false;
    /**
     * this happens when the monster dies
     * @param Defeater the one who killed the monster, usually the player
     * @return the amount of experience to give the defeater
     */
    @Override
    public int Die(Character Defeater) {
        int DifLvl = Level - Defeater.Level;
        int exp = 10 + DifLvl;
        if (exp < 1) exp = 1;

        if (this.isBoss){
            exp *= 3;
            //do lvl game level
        }

        return exp;
    }

    /**
     * forces all who extends this class to have a failAttack method, for when the monster failed to attack
     */
    public abstract void failAttack();

    /**
     * lvlup the monster and add random stats
     */
    public void LvlUp() {
        Level += 1;
        //Health
        /*(L * 100 + D) * ?>0(1. 1I)
        */

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
        Maxhealth = (int)Math.round(((Level * 100) + DefensePower) * (1+ (0.1 * Intelligence)));
        if(isBoss){
            Maxhealth *= 1.5;
        }
        Heal(100);
    }
}

/**
 * an enum of all the monsters possible to spawn
 */
enum Monsters {
    Mage,SkeletonArcher,Slime,Zombie;

    /**
     * gets the name of the monster at the specified index
     * @param i index to look up
     * @return name  of the monster class
     */
    public static Monsters get(int i) {
        return values()[i];
    }
}

