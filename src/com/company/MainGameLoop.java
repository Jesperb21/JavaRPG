package com.company;

import com.company.MonsterTypes.Mage;

import java.awt.*;
import java.util.ArrayList;

public class MainGameLoop {

    private static Map GameMap;

    /**
     * Main Game construcktor.
     * Loading / reloading a new game.
     * @param args Non use.
     */
    public static void main(String[] args) {
        GameMap = new Map();

        Interact(Dialog.Wellcome);

        //The game loop
        while (true){
            TurnLoop();
        }
    }

    enum MoveDir{
        Up,Down,Left,Right;
        public static MoveDir get(int i){
            return values()[i];
        }
    }
    /**
     * Updates each character in the game by turn
     */
    private static void TurnLoop(){
        for(Character charector: GameMap.Characters){
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
                        }
                    }
                }
                //endregion
            }
            //endregion
            else if (charector instanceof Player){
                do{
                    Object respons = Interact(Dialog.Move);
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
                        }
                        Object checkPos = GameMap.fetchAt(newPos);
                        if (checkPos instanceof Integer){
                            if ((Integer)checkPos == 0){
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[newPos.x][newPos.y] = charector;
                                break;
                            }
                            else if ((Integer)checkPos == 1 ||(Integer)checkPos == -1){
                                Console.Msg("You have ran into a wall.",true);
                            }
                        }
                        else if (checkPos instanceof Monster){
                            boolean fightResult = CombatLoop((Player)charector, (Monster)checkPos);
                            if (fightResult){
                                Console.Msg("Hurray, you have defeated a monster",true);
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[newPos.x][newPos.y] = charector;
                            }
                            else {
                                Console.Msg("Sorry, but you have been defeated.",true);
                            }
                        }
                    }
                }while(true);
            }
        }
    }

    /**
     * Active Combat loop
     * @param player the fighting player
     * @param monster the fighting monset
     * @return Returns true if player wins the fight
     */
    private static boolean CombatLoop(Player player, Monster monster){
        return false;
        /*
        while (player.entity.CurrentHealth > 0){
            int damage = player.Attack();
            monster.entity.TakeDamage(damage);
            if (monster.entity.CurrentHealth <= 0){
                monster.Die();
                return true;
            }
            else {
                damage = monster.Attack();
                player.entity.TakeDamage(damage);
            }
        }
        return false;*/
    }


    enum Dialog {
        Wellcome,Action,Move;
    }

    /**
     * Interacts with user
     * @param display Type of dialog
     * @return Action required from user
     */
    private static Object Interact(Dialog display){
        switch(display){
            case Action:
            default:

                break;
            case Move:
                while(true){
                    Console.Msg("Where do you want to go.",false);
                    String Response = Console.readLine().toLowerCase();
                    if (Response.equals("up") || Response.equals("u")){
                        return MoveDir.Up;
                    }
                    else if (Response.equals("down")|| Response.equals("d")){
                        return MoveDir.Down;
                    }
                    else if (Response.equals("left") || Response.equals("l")){
                        return MoveDir.Left;
                    }
                    else if (Response.equals("right")|| Response.equals("r")){
                        return MoveDir.Right;
                    }
                }
            case Wellcome:
                Console.Msg("Hello and welcome to the Java Rpg,",false);
                Console.Msg("also known as, The Tower Of Doom.",false);
                Console.Msg("Created by Jesper Baunsgaard and Daniel Jensen",true);
                break;
        }
        return "";
    }
}
