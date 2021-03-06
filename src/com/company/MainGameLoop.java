package com.company;

import java.awt.*;
import java.util.ArrayList;

/**
 * handles the entire game loop
 */
public class MainGameLoop {
    private static Map GameMap;
    public static int GameLevel = 1;

    /**
     * Main Game construcktor.
     */
    public void StartGame() {
        Console.Interact(Dialog.Wellcome);

        boolean repeatQuestion = true;
        while (repeatQuestion) {
            Console.Msg("Load a game(Y/N)?", false, false);
            String response = Console.readLine();
            if (response instanceof String) {
                if (response.equals("y")){
                    String saveName = (String) Console.Interact(Dialog.load);
                    SQLHandler sqlHandler = new SQLHandler();

                    GameMap = new Map(sqlHandler.loadSave(saveName));
                    repeatQuestion = false;
                }else if (response.equals("n")){
                    GameMap = new Map();
                    repeatQuestion = false;
                }
            }
        }

        //The game loop
        while (true){
            TurnLoop();
        }
    }

    /**
     * Updates each character in the game by turn
     * both monsters and player,
     * combat and move
     */
    private static void TurnLoop(){
        for (int i = 0; i < GameMap.Characters.toArray().length; i++) {
            Character charector = GameMap.Characters.get(i);

            Point ChaPos = GameMap.whereIs(charector);
            //region Bot
            if (charector instanceof Monster) {
                //region Bot Move
                boolean move = true;
                ArrayList<MoveDir> dir = new ArrayList<MoveDir>();
                while (move){
                    MoveDir def;
                    if (dir.toArray().length >= 4){
                        move = false;
                        break;
                    }
                    do {
                        def = MoveDir.get(Console.RandomInt(0, 4));
                    } while (dir.contains(def));
                    dir.add(def);

                    Point newPos = null;
                    switch (def){
                        case Up:
                            newPos = new Point(ChaPos.x,ChaPos.y-1);
                            break;
                        case Down:
                            newPos = new Point(ChaPos.x,ChaPos.y+1);
                            break;
                        case Left:
                            newPos = new Point(ChaPos.x-1,ChaPos.y);
                            break;
                        case Right:
                            newPos = new Point(ChaPos.x+1,ChaPos.y);
                            break;
                    }
                    Object point = GameMap.fetchAt(newPos);
                    if (point instanceof Integer){
                        if ((Integer)point == 0){
                            GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                            GameMap.Map[newPos.x][newPos.y] = charector;
                            move = false;
                            if(((Monster) charector).isBoss){
                                GameMap.addToVisibleMap(newPos, false);
                            }
                        }
                    }
                }
                //endregion
            }
            //endregion
            //region charMove
            else if (charector instanceof Player){
                do{
                    GameMap.printVisibleMap();
                    Object respons = Console.Interact(Dialog.Move);
                    if (respons instanceof MoveDir){
                        Point newPos = null;

                        switch ((MoveDir)respons){
                            case Up:
                                newPos = new Point(ChaPos.x,ChaPos.y-1);
                                break;
                            case Down:
                                newPos = new Point(ChaPos.x,ChaPos.y+1);
                                break;
                            case Left:
                                newPos = new Point(ChaPos.x-1,ChaPos.y);
                                break;
                            case Right:
                                newPos = new Point(ChaPos.x+1,ChaPos.y);
                                break;
                            case cheat:
                                int cheatLvl = charector.Level + 10;
                                if (cheatLvl > 100) break;
                                while (charector.Level != cheatLvl){
                                    charector.LvlUp();
                                }
                                break;
                            case save:
                                GameMap.saveMap();
                                break;
                        }
                        if (newPos == null) break;
                        Object checkPos = GameMap.fetchAt(newPos);
                        if (checkPos instanceof Integer){
                            if ((Integer)checkPos == 0){
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[newPos.x][newPos.y] = charector;
                                GameMap.addToVisibleMap(newPos, true);
                                break;
                            }
                            else if ((Integer)checkPos == 1 ||(Integer)checkPos == -1){
                                GameMap.printVisibleMap();
                                Console.Msg("You have run into a wall.",true,false);
                            }
                        }
                        else if (checkPos instanceof Character){
                            Console.Msg("You have found a " + checkPos.getClass().getSimpleName() + " level " +
                                    (((Character) checkPos).Level) + " and have entered combat.",true,true);
                            boolean fightResult = CombatLoop((Player)charector, (Character)checkPos);
                            if (fightResult){
                                Console.YarhAsciiArt();
                                if (checkPos instanceof Monster){
                                    if(((Monster) checkPos).isBoss){
                                        Console.Msg("YUSSAH, you have defeated the boss, time to move to the next floor", true, false);
                                        GameLevel ++;
                                        GameMap.LoadNextMap();
                                        continue;
                                    }else {
                                        Console.Msg("Hurray, you have defeated a " + checkPos.getClass().getSimpleName(), true, false);
                                    }
                                }
                                else {
                                    Console.Msg("Hurray, you have defeated a The other player", true, false);
                                }
                                ((Player) charector).getExperience(((Character) checkPos).Die(charector));
                                GameMap.Characters.remove(checkPos);

                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[newPos.x][newPos.y] = charector;
                                GameMap.addToVisibleMap(newPos, true);

                                GameMap.addNewCharacter(1, charector.Level);//add new character to map
                                charector.Heal(100);
                                break;
                            }
                            else {
                                Console.Msg("Sorry, but you have been defeated.", true, true);
                                charector.Die((Character)checkPos);
                                //A chance to farm xp
                                //((Monster) checkPos).Heal(100);
                                break;
                            }
                        }
                    }
                }while(true);
            }
            //endregion
        }
    }

    /**
     * Active Combat loop
     * @param player the fighting player
     * @param monster the fighting monset
     * @return Returns true if player wins the fight
     */
    private static boolean CombatLoop(Player player, Character monster){
        while (player.CurrentHealth > 0){
            Console.BattleAsciiArt(player,monster);
            int damage = player.Attack();
            monster.TakeDamage(damage);
            if (monster.CurrentHealth <= 0){
                return true;
            }
            else {
                damage = monster.Attack();
                player.TakeDamage(damage);
            }
        }
        return false;
    }
}
