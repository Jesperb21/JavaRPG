package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Zombie extends Monster{
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1,3);
        switch (ran){
            case 1:
            case 3:
                Damage = MeleeAtt();
                Console.Msg("Zombie used Melee and dealt " + Damage + " Damage", false);
                break;
            default:
                failAttack();
                break;
        }
        return Damage;
    }
    public int MeleeAtt(){
        //Melee
        /*(L * 10 + D) * ?>0(1 .22S - .2I)
        */
        int Damage = (int)Math.round(((Level * 10) + DefensePower) * (1 + ((0.22 * Strength) - (0.2 * Intelligence))));
        return Damage;
    }
    @Override
    public void failAttack(){
        Console.Msg("Zombie fell upon it owns feat and failed the attack", false);
    }
}
