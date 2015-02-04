package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class Slime extends Monster{
    /**
     * Slime do Attack
     * @return amount of damage
     */
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1, 5);
        /**
         * the random is noticeably less likely to pick the start and the ending value, so those result in a failAttack()
         */
        switch (ran){
            case 2:
                Damage = MagicAtt();
                Damage = randomDamage(Damage);
                ran = Console.RandomInt(1, 2);
                switch (ran){
                    case 1:
                        Console.Msg("The Slime spits on you and dealt " + Damage + " magic Damage", false,true);
                        break;
                    case 2:
                        Console.Msg("The Slime farted and dealt " + Damage + " magic Damage", false,true);
                        break;
                }
                break;
            case 3:
                Damage = MeleeAtt();
                Damage = randomDamage(Damage);
                ran = Console.RandomInt(1, 2);
                switch (ran){
                    case 1:
                        Console.Msg("The Slime hit you and dealt " + Damage + " Damage", false,true);
                        break;
                    case 2:
                        Console.Msg("You got stuck in the Slime and took " + Damage + " Damage", false,true);
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
     * Slime failAttack, display message.
     */
    @Override
    public void failAttack() {
        switch (Console.RandomInt(1,3)){
            case 1:
                Console.Msg("The Slime got stuck on a stick and failed to move.", true,true);
                break;
            case 2:
                Console.Msg("The Slime slipped into a hole and struggles to get up.", true,true);
                break;
            case 3:
                Console.Msg("The Slime is still laying in pisces from you last attack, but is gathering itself.", true,true);
                break;
        }
    }
}
