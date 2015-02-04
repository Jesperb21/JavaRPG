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
    public Object[][] VisibleMap;
    private String _defaultMapPath = "src/com/company/maps/";
    private double _enemySpawnChance = 0.2;

    /**
     * Constructor
     */
    public Map() {
        LoadWorld(_defaultMapPath);
    }

    /**
     * loads a random map from the maps folder
     *
     * @param Path path to map folder
     * @return a map with characters and players added
     */
    private void LoadWorld(String Path){
        Player player = new Player();
        LoadWorld(Path, player);
    }
    private void LoadWorld(String Path, Player player) {
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
        boolean bossPlaced = false;
        Point playerPlacedHere = new Point(0,0);
        while (!playerPlaced && !bossPlaced) {//if player & boss wasn't placed after first attempt to generate map, retry!
            for (int i = 0; i < lines.get(0).length(); i++) {
                for (int j = 0; j < lines.size(); j++) {
                    char c = lines.get(j).charAt(i);
                    Object object = c;
                    if (c == '0') {
                        object = 0;
                        if (!playerPlaced && random.nextDouble() < 0.1) {//only place a player if it hasn't been placed already
                            Characters.add(player);
                            object = player;

                            playerPlacedHere = new Point(i,j);

                            playerPlaced = true;
                        } else if(!bossPlaced && random.nextDouble() < 0.1) {//only place a boss if it hasn't been placed earlier
                            Monster boss = CreateRandomMonster();
                            boss.isBoss = true;
                            object = boss;
                            bossPlaced = true;

                        }else if (random.nextDouble() < _enemySpawnChance) {//you cannot place an enemy on the same spot as a player
                            object = CreateRandomMonster();
                        }
                    } else if (c == '1') {
                        object = 1;
                    }
                    map[i][j] = object;
                }
            }
        }
        this.Map = map;
        VisibleMap = new Object[map.length][map[0].length];
        for (int i = 0; i < VisibleMap.length; i++) {
            Arrays.fill(VisibleMap[i],0);
        }
        addToVisibleMap(playerPlacedHere, true); //make the map the player can see after generating the entire game map
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
                return 1;
            }
        } else {
            return -1;
        }
    }

    /**
     * adds points around a given point to the visible map for the player
     * @param p the point of the center visible point for the player
     * @param player = if this is true it means that its the player that added a point to the map, false and its the boss
     */
    public void addToVisibleMap(Point p, boolean player) {
        Point[] pointsToAdd = new Point[4];
        pointsToAdd[0] = new Point(p.x, p.y+1);
        pointsToAdd[1] = new Point(p.x, p.y-1);
        pointsToAdd[2] = new Point(p.x+1, p.y);
        pointsToAdd[3] = new Point(p.x-1, p.y);
        for (Point po : pointsToAdd) {
            Object checkPos = fetchAt(po);
            if (checkPos instanceof Integer) {
                if ((Integer) checkPos == 0) {
                    VisibleMap[po.x][po.y] = " ";
                } else if ((Integer) checkPos == 1) {
                    VisibleMap[po.x][po.y] = "#";
                }
            }else {
                VisibleMap[po.x][po.y] = "#";
            }
        }
        if(player) {
            VisibleMap[p.x][p.y] = "X";
        }else {
            VisibleMap[p.x][p.y] = "B";
        }
    }


    /**
     * prints the map thats visible for the player
     */
    public void printVisibleMap(){
        String Message = "";
        for (int i = 0; i < VisibleMap[0].length; i++) {
            String line = "";
            for (int j = 0; j < VisibleMap.length; j++) {
                if (VisibleMap[j][i] instanceof String){
                    line += VisibleMap[j][i];
                }else {
                    line += " ";
                }
            }
            Message += line + "\n";
        }
        Console.Msg(Message, false,true);
    }
    /**
     * Create a new monster
     * @return The New borne monster
     */
    private Monster CreateRandomMonster() {
        return CreateRandomMonster(1);
    }
    /**
     * Create a new monster with
     * @return The New borne monster
     */
    private Monster CreateRandomMonster(int level) {
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
        return monster;
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
