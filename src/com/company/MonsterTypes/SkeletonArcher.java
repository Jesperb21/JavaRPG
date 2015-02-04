package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class SkeletonArcher extends Monster{
    /**
     * SkeletonArcher do Attack
     * @return amount of damage
     */
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1, 3);
        /**
         * the random is noticeably less likely to pick the start and the ending value, so those result in a failAttack()
         */
        switch (ran){
            case 2:
                Damage = randomDamage(RangedAtt());
                ran = Console.RandomInt(1, 3);
                switch (ran) {
                    case 1:
                        Console.Msg("The SkeletonArcher threw one of its leg at you and dealt " + Damage + " Damage", false, true);
                        break;
                    case 2:
                        Console.Msg("The SkeletonArcher threw it bow at you and dealt " + Damage + " Damage", false, true);
                        break;
                    case 3:
                        Console.Msg("In a quick draw mistook the SkeletonArcher it´s skull for an arrow,\n" +
                                "but fired anyway and dealt " + Damage + " Damage", false, true);

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
     * SkeletonArcher failAttack, display message.
     */
    @Override
    public void failAttack(){
        switch (Console.RandomInt(1,3)){
            case 1:
                Console.Msg("The Skeleton lost its arm and couldn't tighten the bow.", true,true);
                break;
            case 2:
                Console.Msg("The Skeleton body is wandering around looking for the skull.", true,true);
                break;
            case 3:
                Console.Msg("The Skeleton got attacked by a dog that thought it was it´s chewing bone.", true,true);
                break;
        }
    }
}
