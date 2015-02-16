package com.company;

import com.company.MonsterTypes.Mage;
import com.company.MonsterTypes.SkeletonArcher;
import com.company.MonsterTypes.Slime;
import com.company.MonsterTypes.Zombie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MonsterTest {
    List<Monster> monsters = new ArrayList<Monster>();
    public MonsterTest(){
        monsters.add(new Zombie());
        monsters.add(new Mage());
        monsters.add(new Slime());
        monsters.add(new SkeletonArcher());
    }

    @Test
    public void testAttack() {
        for (Monster m : monsters){
            assertNotSame("somehow the ".concat(m.toString()).concat(" used a null attack"), null, m.Attack());
        }
    }

    @Test
    public void testLvlUp() {
        for (Monster m : monsters){
            int oldLevel = m.Level;
            m.LvlUp();
            int newlevel = m.Level;

            assertSame("the ".concat(m.toString()).concat(" didn't level up properly"),oldLevel+1, newlevel);
        }
    }
    @Test
    public void testDie() {
        Player p = new Player();
        for (Monster m : monsters){
            assertNotSame("the ".concat(m.toString()).concat(" didn't die"),null,m.Die(p));
        }
    }
}