package com.company;

public class Player extends Character implements IUser{
    //region ICharacter

    /**
     * shows the attack dialog for the player
     * @return the amount of damage the player dealt
     */
    @Override
    public int Attack() {
        Object UserChoose;
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

    /**
     * when the player dies this happens
     * @param Defeater the character that defeated the player
     * @return returns the amount of exp the defeater is given, always 0 in this case
     */
    @Override
    public int Die(Character Defeater) {
        Heal(100);
        return 0;
    }
    //endregion

    //region IUser

    /**
     * LvlUp levels the player up and shows a dialog that lets the player pic a stat to increase, recalculating hp
     */
    @Override
    public void LvlUp() {
        Level += 1;

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
        //Health
        /*(L * 100 + D) * ?>0(1. 1I)
        */
        Maxhealth = (int)Math.round(((Level * 100) + DefensePower) * (1+ (0.1 * Intelligence)));
        Heal(100);
    }

    /**
     * gives the player experience, calls LvlUp if at 100 exp or more
     * @param Exp amount to give the player
     */
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
