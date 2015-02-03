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

    @Override
    public void failAttack(){
        Console.Msg("Zombie fell upon it owns feat and failed the attack", false);
    }
}
