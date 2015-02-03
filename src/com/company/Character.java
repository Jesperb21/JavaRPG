package com.company;

public abstract class Character implements ICharacter{
    public int Maxhealth = 100;
    public int CurrentHealth = Maxhealth;

    /**
     * Calculate Damage on character
     * @param damage amount of damage thrown at character
     */
    public void TakeDamage(int damage){
        int def = (int)Math.floor(damage*(1-((1+DefensePower)/100)));
        Console.Msg("But the " + this.getClass() + " deflected " + (damage - def) + " damage",true);
        CurrentHealth -= def;
    }

    public int Level = 1;        // 1 - 101
    public int Strength = 0;     // % [0-100]   -S**A
    public int DefensePower = 0; // % [0-100]   -SD**
    public int Intelligence = 0; // % [0-100]   -SDI*
    public int Agility = 0;      // % [0-100]   -**IA
    public int Experience = 0;   // % [0-100]

    //Health
    /*(L * 100 + D) * ?>0(1. 1I)
    */

    //Defense
    /*(1) + D
    */

    //Melee
    /*(L * 10 + D) * ?>0(1 .22S - .2I)
    */

    //Magic
    /*L * 10 * ?>0(1 .2I + .1A)
    */

    //Ranged
    /*L * 10 * ?>0(1 .2A + .1S)
    */

    /**
     * Character constructor
     */
    Character(){
        if (this instanceof Player){

        }
        else {
            Level = Console.RandomInt(1,10);
        }
        Maxhealth = (int)Math.round((Level * 100 + DefensePower) * (1 + (0.1 * Intelligence)));
    }

    /**
     * Heal the character % amount
     * @param Healvalue The healing value based as %
     */
    public void Heal(int Healvalue){}
}
