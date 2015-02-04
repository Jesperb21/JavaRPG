package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public final class Console {
     /**
     * Clears console "Not working
     */
    public final static void Clear()
    {
        try
        {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //e.printStackTrace();
            //  Handle any exceptions.
        }
    }

    /**
     * Write Message in console
     * @param msg The message to be shown
     * @param Wait Shall the console Wait for user to respond.
    */
    public final static void Msg(String msg, boolean Wait)
    {
        System.out.println(msg);
        if (Wait){
            System.out.println("Please press 'enter' to continue");
            Console.readLine();
        }
    }

    private final static Random ran = new Random();

    /**
     * Get random int
     * @param min Minimum int
     * @param max Maximum int
     * @return returns a int between min and max
     */
    public final static int RandomInt(int min, int max){
        return min + ran.nextInt(Math.abs(max-min));
    }

    /**
     * generate a random double between between a min and a max
     * @param min minimum value
     * @param max maximum value
     * @return returns a random value
     */
    public final static double RandomDouble(double min, double max){
        return min + (ran.nextDouble()*(max-min));
    }


    /**
     * Read Console line
     * @return a string with user response.
     */
    public final static String readLine()
    {
        try{
            java.io.Console console = System.console();
            if (console != null)
            {
                return console.readLine();
            }
            else
            {
                return new BufferedReader(new InputStreamReader(System.in)).readLine();
            }
        }catch(Exception ex){
            // if any error occurs
            //ex.printStackTrace();
        }
        return  "";
    }

    /**
     * Interacts with user
     * @param display Type of dialog
     * @return Action required from user
     */
    public final static Object Interact(Dialog display){
        switch(display){
            case Level:
                while(true){
                    Console.Msg("Congratulation you a gain a level.",false);
                    Console.Msg("Choose a stat to improve.",false);
                    Console.Msg("1. Strength",false);
                    Console.Msg("2. DefensePower",false);
                    Console.Msg("3. Intelligence",false);
                    Console.Msg("4. Agility",false);
                    String Response = Console.readLine().toLowerCase();

                    if (Response.equals("1") || Response.equals("2") || Response.equals("3") || Response.equals("4")){
                        return Integer.parseInt(Response);
                    }
                }
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
            case Attack:
                while(true){
                    Console.Msg("Choose you attack.",false);
                    Console.Msg("1. Melee",false);
                    Console.Msg("2. Ranged",false);
                    Console.Msg("3. Magic",false);
                    Console.Msg("4. Heal",false);

                    String Response = Console.readLine().toLowerCase();

                    if (Response.equals("1") || Response.equals("2") || Response.equals("3") || Response.equals("4")){
                        return Integer.parseInt(Response);
                    }
                }
            case Wellcome:
                Console.Msg("Hello and welcome to the Java Rpg,",false);
                Console.Msg("also known as, The Tower Of Doom.",false);
                Console.Msg("Created by Jesper Baunsgaard and Daniel Jensen",true);
                break;
            default:
                break;
        }
        return "";
    }
}

/**
 * Move directions
 */
enum MoveDir{
    Up,Down,Left,Right;
    public static MoveDir get(int i){
        return values()[i];
    }
}

/**
 * Type of dialog to communicate with user,
 * used in Interact
 */
enum Dialog {
    Wellcome,Move,Attack,Level;
}
