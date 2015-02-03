package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Zombie extends Monster{
    @Override
    public int Attack() {
        int Damage = MeleeAtt();

        Console.Msg("Zombie used Melee and dealt " + Damage + " Damage", false);

        return Damage;
    }
    public int MeleeAtt(){
        //Melee
        /*(L * 10 + D) * ?>0(1 .22S - .2I)
        */
        int Damage = (int)Math.round(((Level * 10) + DefensePower) * (1 + ((0.22 * Strength) - (0.2 * Intelligence))));
        return Damage;
    }
}
