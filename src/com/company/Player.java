package com.company;

public class Player extends Character implements IUser{
    //region ICharacter
    @Override
    public int Attack() {
        return 0;
    }

    @Override
    public void Die() {

    }
    //endregion

    //region IUser
    @Override
    public void LvlUp() {

    }

    @Override
    public int RangedAtt() {
        return 0;
    }

    @Override
    public int MeleeAtt() {
        return 0;
    }

    @Override
    public int MagicAtt() {
        return 0;
    }
    //endregion
}
