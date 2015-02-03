package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Slime extends Monster{
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1, 5);
        /**
         * the random is noticeably less likely to pick the start and the ending value, so those result in a failAttack()
         */
        switch (ran){
            case 2:
                Damage = RangedAtt();
                Damage = randomDamage(Damage);
                Console.Msg("The Slime used Ranged attack and dealt " + Damage + " Damage", false);
                break;
            case 3:
                Damage = MeleeAtt();
                Damage = randomDamage(Damage);
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
