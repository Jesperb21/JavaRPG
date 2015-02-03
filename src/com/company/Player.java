package com.company;

public class Player extends Character implements IUser{
    //region ICharacter
    @Override
    public int Attack() {
        Object UserChoose= null;
        do {
            UserChoose = Console.Interact(Dialog.Attack);
        }while (!(UserChoose instanceof Integer) && ((Integer)UserChoose < 1 || (Integer)UserChoose > 4));
        int Damage = 0;
        switch ((Integer)UserChoose){
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
                //Healing
                /*(L * 10) * ?>0(1. 1I)
                */
                int Healing = (int)Math.round((Level * 100 + DefensePower) * (1 + (0.1 * Intelligence)));

                Console.Msg("You used Heal and Healed you self with " + Healing + "%",false);
                Heal(Healing);
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
    //endregion
}
