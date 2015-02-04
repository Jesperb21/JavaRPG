package com.company;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Map {
    public List<Character> Characters = new ArrayList<Character>();
    public Object[][] Map;
    private String _defaultMapPath = "src/com/company/maps/";
    private double _enemySpawnChance = 0.9;

    /**
     * Constructor
     */
    public Map() {
        Map = LoadWorld(_defaultMapPath);
    }

    /**
     * loads a random map from the maps folder
     *
     * @param Path path to map folder
     * @return a map with characters and players added
     */
    private Object[][] LoadWorld(String Path) {
        File MapsFolder = new File(Path);
        File[] ListOfFiles = MapsFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.toString().toLowerCase().endsWith(".map");
            }
        });
        Random random = new Random();

        File mapFile = ListOfFiles[random.nextInt(ListOfFiles.length)];

        List<String> lines = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(mapFile);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Object[][] map = new Object[lines.get(0).length()][lines.size()];
        boolean playerPlaced = false;
        while (!playerPlaced) {//if player wasn't placed after first attempt to generate map, retry!
            for (int i = 0; i < lines.get(0).length(); i++) {
                for (int j = 0; j < lines.size(); j++) {
                    char c = lines.get(j).charAt(i);
                    Object object = c;
                    if (c == '0') {
                        object = 0;
                        if (!playerPlaced && random.nextDouble() < 0.1) {//only place a player if it hasnt been placed already
                            Player p = new Player();
                            Characters.add(p);
                            object = p;

                            playerPlaced = true;
                        } else if (random.nextDouble() < _enemySpawnChance) {//you cannot place an enemy on the same spot as a player
                            object = CreateRandomMonster();
                        }
                    } else if (c == '1') {
                        object = 1;
                    }
                    map[i][j] = object;
                }
            }
        }

        return map;
    }

    /**
     * fetches the position of a character
     *
     * @param c character to return position of
     * @return position of the character if the character is on the map, null if not
     */
    public Point whereIs(Character c) {
        for (int i = 0; i < Map.length; i++) {
            if (Arrays.asList(Map[i]).contains(c)) {
                return new Point(i, Arrays.asList(Map[i]).indexOf(c));
            }
        }
        return null;
    }

    /**
     * fetches the object at the given pos on the map
     *
     * @param p position to fetch from
     * @return object if valid point, -1 if not
     */
    public Object fetchAt(Point p) {
        if (p.x >= 0 && p.x <= (Map.length - 1) && p.y >= 0 && p.y <= (Map[0].length - 1)) {
            if (Map[p.x][p.y] != null) {
                return Map[p.x][p.y];
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * Create a new monster
     * @return The New borne monster
     */
    private Object CreateRandomMonster() {
        return CreateRandomMonster(1);
    }
    /**
     * Create a new monster with
     * @return The New borne monster
     */
    private Object CreateRandomMonster(int level) {
        Class<?> r;
        Monster monster = null;
        try {
            r = Class.forName("com.company.MonsterTypes." + String.valueOf(Monsters.get(Console.RandomInt(0, Monsters.values().length - 1))));
            monster = (Monster) r.newInstance();
            level =  Console.RandomInt((level<=2)?1:level-2,level+5);
            while (monster.Level < level){
                monster.LvlUp();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Characters.add(monster);
        return (Object) monster;
    }

    /**
     * Add a new character to the map
     * @param AmountToAdd The Amount of new characters / monsters
     * @param level the Player current level
     */
    public void addNewCharacter(int AmountToAdd,int level) {
        while (AmountToAdd > 0) {
            for (int i = 0; i < Map.length && AmountToAdd > 0; i++) {
                for (int j = 0; j < Map[0].length && AmountToAdd > 0; j++) {
                    Object o = Map[i][j];
                    if ((o instanceof Integer) && ((Integer) o == 0)) {
                        if (Console.RandomDouble(0, 1) > _enemySpawnChance) {
                            Map[i][j] = CreateRandomMonster(level);
                            AmountToAdd--;
                        }
                    }
                }
            }
        }
    }
}
