package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Zombie extends Monster{
    /**
     * Zombie do Attack
     * @return amount of damage
     */
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1,4);
        /**
         * the random is noticeably less likely to pick the start and the ending value, so those result in a failAttack()
         */
        switch (ran){
            case 1:
            case 3:// 2 of these to get a higher chance of doing damage...
                Damage = MeleeAtt();
                Damage = randomDamage(Damage);
                Console.Msg("Zombie used Melee and dealt " + Damage + " Damage", false);
                break;
            default:
                failAttack();
                break;
        }
        return Damage;
    }

    /**
     * Zombie failAttack, display message.
     */
    @Override
    public void failAttack(){
        Console.Msg("Zombie fell upon it owns feat and failed the attack", false);
    }
}
