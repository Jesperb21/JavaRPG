package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterTest {
    Character character;
    public CharacterTest(){
        character = new Character() {
            @Override
            public int Attack() {
                return 0;
            }

            @Override
            public int Die(Character Defeater) {
                return 0;
            }

            @Override
            public void LvlUp() {

            }
        };
    }

    public void MockUser(String Message){
        System.setIn(new java.io.ByteArrayInputStream(Message.getBytes()));
    }
    public void MockUser(){MockUser("\n");}

    @Test
    public void testTakeDamage() {
        character.TakeDamage(30);
        assertNotSame("it didn't take damage, oh no", character.Maxhealth, character.CurrentHealth);
    }

    @Test
    public void testRangedAtt() {
        assertNotSame("somehow the character attacked with a null arrow", null, character.RangedAtt());
    }

    @Test
    public void testMeleeAtt() {
        assertNotSame("somehow the character attacked with a null punch", null, character.MeleeAtt());
    }

    @Test
    public void testMagicAtt() {
        assertNotSame("somehow the character attacked with null magic", null, character.MagicAtt());
    }

    @Test
    public void testHeal(){
        character.CurrentHealth = 1;
        character.Heal(100);
        assertSame("character didnt heal properly",character.Maxhealth,character.CurrentHealth);
    }

    @Test
    public void testRandomDamage() {
        int maxDmg = 10;
        int minDmg = 7;// math.floor(10.0*0.75);
        int actualDmg = character.randomDamage(maxDmg);
        MockUser();//hit enter

        assertTrue("damage was less than the minimum allowed damage", actualDmg >= minDmg);
        assertTrue("damage was more than the maximum allowed damage", actualDmg <= maxDmg);
    }
}