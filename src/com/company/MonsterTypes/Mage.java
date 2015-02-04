package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

import java.lang.reflect.Field;

public class Mage extends Monster{
    /**
     * Mage do Attack
     * @return amount of damage
     */
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1,3);
        /**
         * the random is noticeably less likely to pick the start and the ending value, so those result in a failAttack()
         */
        switch (ran){
            case 2:
                Damage = randomDamage(MagicAtt());
                Console.Msg("Mage used Magic and dealt " + Damage + " Damage", false);
                break;
            default:
                failAttack();
                break;
        }
        return Damage;
    }

    /**
     * Mage failAttack, display message.
     */
    @Override
    public void failAttack() {
        Console.Msg("The Mage stumble upon its tongue, and couldn't pronounce the spell.", false);
    }
}
