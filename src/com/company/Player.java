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
                Damage = randomDamage(MeleeAtt());
                Console.Msg("You used Melee and dealt " + Damage + " Damage",false);
                break;
            case 2:
                Damage = randomDamage(RangedAtt());
                Console.Msg("You used Ranged and dealt " + Damage + " Damage",false);
                break;
            case 3:
                Damage = randomDamage(MagicAtt());
                Console.Msg("You used Magic and dealt " + Damage + " Damage",false);
                break;
            case 4:
                //Healing
                /*(L * 10) * ?>0(1. 1I)
                */
                int Healing = (int)Math.round((Level * 10) * (1 + (0.22 * Intelligence)));
                Healing = randomDamage(Healing);
                Console.Msg("You used Heal and Healed you self with " + Healing + "%",false);
                Heal(Healing);
                Console.Msg("You now have " + CurrentHealth + " hp.", false);
                Damage = 0;
                break;
        }

        return Damage;
    }

    @Override
    public int Die(Character Defeater) {
        Heal(100);
        return 0;
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
        int response = (Integer)Console.Interact(Dialog.Level);
        switch (response){
            case 1:
                Strength++;
                break;
            case 2:
                DefensePower++;
                break;
            case 3:
                Intelligence++;
                break;
            case 4:
                Agility++;
                break;
        }
    }

    @Override
    public void getExperience(int Exp) {
        Experience += Exp;
        while(Experience >= 100){
            Experience -= 100;
            LvlUp();
        }
    }
    //endregion
}
