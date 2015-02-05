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
        LoadWorld(_defaultMapPath,3);
    }

    /**
     * loads a random map from the maps folder
     *
     * @param Path path to map folder
     */
    private void LoadWorld(String Path, int players){
        List<Player> player = new ArrayList<Player>();
        for (int i = 0; i < players; i++) {
            player.add(new Player());
        }
        LoadWorld(Path, player);
    }

    /**
     * loads the next map while saving the player object
     * @param player the player to save
     */
    public void LoadNextMap(List<Player> player){
        LoadWorld(_defaultMapPath, player);
    }

    /**
     * loads a random map from the folder specified with the specific player object
     * @param Path the folder to load maps from
     * @param player the player object to add to the map
     */
    private void LoadWorld(String Path, List<Player> player) {
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
        int playersToPlace = player.size();
        boolean bossPlaced = false;
        List<Point> playersPlacedHere = new ArrayList<Point>();
        while (playersToPlace > 0 && !bossPlaced) {//if player & boss wasn't placed after first attempt to generate map, retry!
            playersToPlace = player.size();
            bossPlaced = false;
            for (int i = 0; i < lines.get(0).length(); i++) {
                for (int j = 0; j < lines.size(); j++) {
                    char c = lines.get(j).charAt(i);
                    Object object = c;
                    if (c == '0') {
                        object = 0;
                        if (playersToPlace != 0 && random.nextDouble() < 0.02) {//only place a player if it hasn't been placed already
                            Characters.add(player.get(playersToPlace-1));
                            object = player.get(playersToPlace-1);

                            playersPlacedHere.add(new Point(i,j));

                            playersToPlace --;
                        } else if(!bossPlaced && random.nextDouble() < 0.02) {//only place a boss if it hasn't been placed earlier
                            Monster boss = CreateRandomMonster();
                            boss.isBoss = true;

                            while (boss.Level < MainGameLoop.GameLevel * 10){
                                boss.LvlUp();
                            }
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
        for(Point p : playersPlacedHere) {
            addToVisibleMap(p, true); //make the map the player can see after generating the entire game map
        }
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
        pointsToAdd[0] = new Point(p.x, p.y + 1);
        pointsToAdd[1] = new Point(p.x, p.y - 1);
        pointsToAdd[2] = new Point(p.x + 1, p.y);
        pointsToAdd[3] = new Point(p.x - 1, p.y);
        if(player) {
            for (Point po : pointsToAdd) {
                Object checkPos = fetchAt(po);
                if (checkPos instanceof Integer) {
                    if ((Integer) checkPos == 0) {
                        VisibleMap[po.x][po.y] = " ";
                    } else if ((Integer) checkPos == 1) {
                        VisibleMap[po.x][po.y] = "#";
                    }
                } else {
                    if (checkPos instanceof Monster && ((Monster) checkPos).isBoss) {
                        VisibleMap[p.x][po.y] = "B";
                    }else {
                        VisibleMap[po.x][po.y] = " ";
                    }
                }
            }
            VisibleMap[p.x][p.y] = "X";
        }else {
            for (Point po : pointsToAdd) {
                if (po.x != -1 && po.y != -1 && po.x < VisibleMap.length && po.y < VisibleMap[0].length) {
                    if (VisibleMap[po.x][po.y] instanceof String && VisibleMap[po.x][po.y].equals("B")) {
                        VisibleMap[po.x][po.y] = " ";
                    }
                }
            }
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
