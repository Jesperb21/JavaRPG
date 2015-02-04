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
                ran = Console.RandomInt(1, 3);
                switch (ran) {
                    case 1:
                        Console.Msg("The Zombie hit´s you, dealt " + Damage + " Damage, and dropped it´s arm", false, true);
                        break;
                    case 2:
                        Console.Msg("the Zombie bits you and deals " + Damage + " Damage", false, true);
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
     * Zombie failAttack, display message.
     */
    @Override
    public void failAttack(){
        switch (Console.RandomInt(1,3)) {
            case 1:
                Console.Msg("The Zombie fell upon it owns feat and failed the attack.", true, true);
                break;
            case 2:
                Console.Msg("The Zombie chased you, but stopped like it forgot what it was doing.", true, true);
                break;
            case 3:
                Console.Msg("The Zombie was too busy chewing on a brain.", true, true);
                break;
        }
    }
}
