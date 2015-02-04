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
                ran = Console.RandomInt(1, 2);
                switch (ran) {
                    case 1:
                        Console.Msg("The Mage threw a fireball and dealt " + Damage + "magic Damage", false, true);
                        break;
                    case 2:
                        Console.Msg("The Mage started a thunder strike and dealt " + Damage + "magic Damage", false, true);
                        break;
                }
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
        switch (Console.RandomInt(1,3)){
            case 1:
                Console.Msg("The Mage stumble upon its tongue, and couldn't pronounce the spell.", true,true);
                break;
            case 2:
                Console.Msg("The Mage is looking in his Magic book to find a casting spell.", true,true);
                break;
            case 3:
                Console.Msg("The Mage have lost his glasses, and fumbles the ground to find them.", true,true);
                break;
        }
    }
}
