package com.company;

public abstract class Character implements ICharacter{
    public int Maxhealth = 100;
    public int CurrentHealth = Maxhealth;

    /**
     * Calculate Damage on character
     * @param damage amount of damage thrown at character
     */
    public void TakeDamage(int damage){
        if (damage != 0){
            //Defense
            /* 1- ((1)+D / 100)
            */
            double d = damage*(1-((double)(1+DefensePower)/100));
            int def = (int)Math.floor(d);
            Console.Msg("But the " + this.getClass().getSimpleName().toString() + " deflected " + (damage - def) + " damage",true);
            CurrentHealth -= def;
        }
    }

    public int Level = 1;        // 1 - 101
    public int Strength = 0;     // % [0-100]   -S**A
    public int DefensePower = 0; // % [0-100]   -SD**
    public int Intelligence = 0; // % [0-100]   -SDI*
    public int Agility = 0;      // % [0-100]   -**IA
    public int Experience = 0;   // % [0-100]

    /**
     * Character constructor
     */
    Character(){
        if (this instanceof Player){
            Maxhealth = (int)Math.round((Level * 100 + DefensePower) * (1 + (0.1 * Intelligence)));
        }
        else {
            Level = Console.RandomInt(1,10);
            Maxhealth = 1;
        }

        //Health
        /*(L * 100 + D) * ?>0(1. 1I)
        */
        //Maxhealth = (int)Math.round((Level * 100 + DefensePower) * (1 + (0.1 * Intelligence)));
        CurrentHealth = Maxhealth;
    }

    public int RangedAtt() {
        //Ranged
        /*L * 10 * ?>0(1 .2A + .1S)
        */
        int Damage = (int)Math.round((Level * 10) * (1 + ((0.2 * Agility) + (0.1 * Strength))));
        return Damage;
    }

    public int MeleeAtt() {
        //Melee
        /*(L * 10 + D) * ?>0(1 .22S - .2I)
        */
        int Damage = (int)Math.round(((Level * 10) + DefensePower) * (1 + ((0.22 * Strength) - (0.2 * Intelligence))));
        return Damage;
    }

    public int MagicAtt() {
        //Magic
        /*L * 10 * ?>0(1 .2I + .1A)
        */
        int Damage = (int)Math.round(((Level * 10) + DefensePower) * (1 + ((0.2 * Intelligence) + (0.1 * Agility))));
        return Damage;
    }

    /**
     * Heal the character % amount
     * @param Healvalue The healing value based as %
     */
    public void Heal(int Healvalue){
        if (CurrentHealth < 0) CurrentHealth = 0;
        CurrentHealth += (Maxhealth/100)*Healvalue;
        if (CurrentHealth > Maxhealth) CurrentHealth = Maxhealth;
    }
    public int randomDamage(int dmg){
        return (int)Console.RandomDouble((dmg*0.75), dmg);
    }
}
