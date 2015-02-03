package com.company;

public class Player extends Character implements IUser{
    //region ICharacter
    @Override
    public int Attack() {
        Object UserChoose= null;
        do {
            Console.Interact(Dialog.Attack);
        }while (!(UserChoose instanceof Integer) && ((Integer)UserChoose < 1 || (Integer)UserChoose > 4));
        int Damage = 0;
        switch ((Integer)Console.Interact(Dialog.Attack)){
            case 1:
                Damage = MeleeAtt();
                Console.Msg("You used Melee and dealt " + Damage + " Damage",false);
                break;
            case 2:
                Damage = RangedAtt();
                Console.Msg("You used Ranged and dealt " + Damage + " Damage",false);
                break;
            case 3:
                Damage = MagicAtt();
                Console.Msg("You used Magic and dealt " + Damage + " Damage",false);
                break;
            case 4:

                Console.Msg("You used Heal and Healed you self with " + Damage + "%",false);
                Heal(Damage);
                Damage = 0;
                break;
        }
        return Damage;
    }

    @Override
    public void Die() {
        Heal(100);
    }
    //endregion

    //region IUser
    @Override
    public void LvlUp() {
        Level += 1;
        //Health
        /*(L * 100 + D) * ?>0(1. 1I)
        */
        Maxhealth = (int)Math.round(((Level * 100) + DefensePower) * (1+ (0.1 * Intelligence)));
        Heal(100);
    }

    @Override
    public int RangedAtt() {
        //Ranged
        /*L * 10 * ?>0(1 .2A + .1S)
        */
        int Damage = (int)Math.round((Level * 10) * (1 + ((0.2 * Agility) + (0.1 * Strength))));
        return Damage;
    }

    @Override
    public int MeleeAtt() {
        //Melee
        /*(L * 10 + D) * ?>0(1 .22S - .2I)
        */
        int Damage = (int)Math.round(((Level * 10) + DefensePower) * (1 + ((0.22 * Strength) - (0.2 * Intelligence))));
        return Damage;
    }

    @Override
    public int MagicAtt() {
        //Magic
        /*L * 10 * ?>0(1 .2I + .1A)
        */
        int Damage = (int)Math.round(((Level * 10) + DefensePower) * (1 + ((0.2 * Intelligence) + (0.1 * Agility))));
        return Damage;
    }
    //endregion
}
