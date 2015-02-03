package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Mage extends Monster{
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1,3);
        switch (ran){
            case 1:
            case 3:
                Damage = MagicAtt();
                Console.Msg("Mage used Magic and dealt " + Damage + " Damage", false);
                break;
            default:
                failAttack();
                break;
        }
        return Damage;
    }

    @Override
    public void failAttack() {
        Console.Msg("The Mage stumble upon its tongue, and couldn't pronounce the spell.", false);
    }
}
