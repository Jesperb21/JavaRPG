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
            Point ChaPos = GameMap.WhereIs(charector);
            //region Bot
            if (charector instanceof Monster) {
                //region Bot Move
                boolean move = true;
                ArrayList<MoveDir> dir = new ArrayList<MoveDir>();
                while (move){
                    MoveDir def;
                    do {
                        def = MoveDir.get(Console.RandomInt(0, 4));
                    } while (dir.contains(def));
                    dir.add(def);

                    switch (def){
                        case Up:
                            if (GameMap.fetchAt(new Point(ChaPos.x,ChaPos.y-1)) == 0){
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[ChaPos.x][ChaPos.y-1] = charector;
                                move = false;
                            }
                            break;
                        case Down:
                            if (GameMap.fetchAt(new Point(ChaPos.x,ChaPos.y+1)) == 0){
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[ChaPos.x][ChaPos.y+1] = charector;
                                move = false;
                            }
                            break;
                        case Left:
                            if (GameMap.fetchAt(new Point(ChaPos.x-1,ChaPos.y)) == 0){
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[ChaPos.x-1][ChaPos.y] = charector;
                                move = false;
                            }
                            break;
                        case Right:
                            if (GameMap.fetchAt(new Pos(ChaPos.x+1,ChaPos.y)) == 0){
                                GameMap.Map[ChaPos.x][ChaPos.y] = 0;
                                GameMap.Map[ChaPos.x+1][ChaPos.y] = charector;
                                move = false;
                            }
                            break;
                    }
                }
                //endregion
            }
            //endregion
            else if (charector instanceof Player){
                boolean combat = false;
                do{
                    Object respons = Interact(Dialog.Move);
                    if (respons instanceof MoveDir){
                        Point newPos;
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
                                GameMap.Map[ChaPos.x+1][ChaPos.y] = charector;
                                break;
                            }
                            else if ((Integer)checkPos == 1 ||(Integer)checkPos == -1){
                                Console.Msg("You have ran into a wall.",true);
                            }
                        }
                        else if (checkPos instanceof Monster){

                        }
                    }
                }while(true);
            }
            //else if (Win){
                //type hura....
            //}
        }
    }
    private boolean CombatLoop(){
        return false;
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
                    Console.Msg("Where do you want to go.",true);
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
                    else if (Response.equals("right")|| Response.equals('r')){
                        return MoveDir.Right;
                    }
                }
            case Wellcome:
                Console.Msg("Hello and welcome to the Java Rpg,",true);
                Console.Msg("also known as, The Tower Of Doom.",false);
                Console.Msg("Created by Jesper Baunsgaard and Daniel Jensen",false);
                break;
        }
        return "";
    }
}
