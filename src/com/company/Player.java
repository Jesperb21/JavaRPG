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
        int ran = 0;
        switch ((Integer)UserChoose){
            case 1:
                Damage = randomDamage(MeleeAtt());
                ran = Console.RandomInt(1, 2);
                switch (ran) {
                    case 1:
                        Console.Msg("You swung your sword, hit some flesh, and dealt " + Damage + " Damage",false,true);
                        break;
                    case 2:
                        Console.Msg("You headbutted your enemy, and dealt " + Damage + " Damage",false,true);
                        break;
                }
                break;
            case 2:
                Damage = randomDamage(RangedAtt());
                ran = Console.RandomInt(1, 2);
                switch (ran) {
                    case 1:
                        Console.Msg("You threw your sword at the monster and dealt " + Damage + " Damage", false, true);
                        break;
                    case 2:
                        Console.Msg("You picked up a stone, threw it at the monster and dealt " + Damage + " Damage", false, true);
                        break;
                }
                break;
            case 3:
                Damage = randomDamage(MagicAtt());
                ran = Console.RandomInt(1, 2);
                switch (ran) {
                    case 1:
                        Console.Msg("You threw a fireball and dealt " + Damage + " magic Damage", false, true);
                        break;
                    case 2:
                        Console.Msg("You started a thunder strike and dealt " + Damage + " magic Damage", false, true);
                        break;
                }
                break;
            case 4:
                //Healing
                /*(L * 10) * ?>0(1. 1I)
                */
                int Healing = (int)Math.round((Level * 10) * (1 + (0.22 * Intelligence)));
                Healing = randomDamage(Healing);
                Console.Msg("You used Heal and Healed you self with " + Healing + "%",false,true);
                Heal(Healing);
                Console.Msg("You now have " + CurrentHealth + " hp.", true);
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
