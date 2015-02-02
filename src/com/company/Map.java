package com.company;

import com.company.MonsterTypes.Monsters;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Map {
    public List<Character> Characters;
    public Object[][] Map;
    private String _defaultMapPath = "./maps/";
    private double _enemySpawnChance = 0.2;

    public Map(){
        Map = LoadWorld(_defaultMapPath);
    }

    /**
     * loads a random map from the maps folder
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

        Object[][] map = new Object[lines.size()][lines.get(0).length()];
        boolean playerPlaced = false;
        while (!playerPlaced) {//if player wasn't placed after first attempt to generate map, retry!
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(0).length(); j++) {
                    char c = lines.get(i).charAt(j);
                    Object object = c;
                    if (c == 0) {
                        if (!playerPlaced && random.nextDouble() > 0.1){//only place a player if it hasnt been placed already
                            Player p = new Player();
                            Characters.add(p);
                            object = p;

                            playerPlaced = true;
                        }else if (random.nextDouble() > _enemySpawnChance) {//you cannot place an enemy on the same spot as a player
                            Class<?> r;
                            Character character = null;
                            try {
                                r = Class.forName(String.valueOf(Monsters.get(random.nextInt(Monsters.values().length))));
                                character = (Character) r.newInstance();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            Characters.add(character);
                            object = character;
                        }
                    }
                    map[i][j] = object;
                }
            }
        }

        return map;
    }

    /**
     * fetches the position of a character
     * @param c character to return position of
     * @return position of the character if the character is on the map, null if not
     */
    public Point whereIs(Character c){
        for (int i = 0; i < Map.length; i++) {
            if(Arrays.asList(Map[i]).contains(c)){
                return new Point(i, Arrays.asList(Map[i]).indexOf(c));
            }
        }
        return null;
    }

    /**
     * fetches the object at the given pos on the map
     * @param p position to fetch from
     * @return object if valid point, -1 if not
     */
    public Object fetchAt(Point p){
        if(Map[p.x][p.y] != null) {
            return Map[p.x][p.y];
        }else {
            return -1;
        }
    }
}