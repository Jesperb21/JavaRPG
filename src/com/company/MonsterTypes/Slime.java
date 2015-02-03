package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Slime extends Monster{
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1, 6);
        switch (ran){
            case 1:
            case 3:
                Damage = RangedAtt();
                Console.Msg("The Slime used Ranged attack and dealt " + Damage + " Damage", false);
                break;
            case 4:
            case 6:
                Damage = MeleeAtt();
                Console.Msg("Slime used Melee and dealt " + Damage + " Damage", false);
                break;
            default:
                failAttack();
                break;
        }
        return Damage;
    }

    @Override
    public void failAttack() {
        Console.Msg("The Slime got stuck on a stick and failed to move.", false);
    }
}
