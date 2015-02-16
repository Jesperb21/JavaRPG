package com.company;

import com.company.MonsterTypes.Zombie;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player = new Player();

    @Test
    public void testAttack() throws Exception {

    }

    @Test
    public void testDie() throws Exception {
        player.CurrentHealth = 1;
        player.Die(new Zombie());

        assertNotSame("player didn't heal after dieing, oh no", 1, player.CurrentHealth);
    }

    @Test
    public void testLvlUp() throws Exception {

    }

    @Test
    public void testGetExperience() throws Exception {

    }
}