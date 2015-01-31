package com.company;

public abstract class Character implements ICharacter{
    public Entity entity;//remove this one?
    public int Level;
    public int AttackPower;
    public int DefensePower;
    public int MagicPower;

    //add value parameter?
    public void Heal(){}
}
