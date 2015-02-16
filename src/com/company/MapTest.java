package com.company;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MapTest {
    List<Player> players;
    Character boss;
    Map map;
    public MapTest(){
        map = new Map();
        players = new ArrayList<Player>();
        //boss;
        for (Character c : map.Characters){
            if (c instanceof Player){
                players.add((Player) c);
            }else if (c instanceof Monster){
                if (((Monster) c).isBoss){
                    boss = c;
                }
            }
        }

    }

    @Test
    public void testLoadNextMap() {
        Map tempMap = new Map();
        Object[][] oldmap = tempMap.Map;
        tempMap.LoadNextMap();
        Object[][] newmap = tempMap.Map;

        assertThat(oldmap, IsNot.not(IsEqual.equalTo(newmap)));
    }

    @Test
    public void testFetchAtAndWhereIs() {
        assertSame("FetchAt or WhereIs is broken",boss,map.fetchAt(map.whereIs(boss)));
    }

    @Test
    public void testAddToVisibleMap() {
        Point p = new Point(5,5);
        map.addToVisibleMap(p, false);
        assertSame("AddToVisibleMap failed","B",map.VisibleMap[5][5]);
    }

    @Test
    public void testAddNewCharacter(){
        int CharAmount = map.Characters.size();
        map.addNewCharacter(1,1);
        int newCharAmount = map.Characters.size();
        assertNotEquals("somehow the amount of characters is the same after adding a new character",CharAmount,newCharAmount);
        assertSame("amount of characters after adding 1 new is not equal to the old value + 1, something went wrong",CharAmount+1, newCharAmount);
    }
}