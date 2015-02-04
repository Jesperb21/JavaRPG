package com.company;

import com.company.MonsterTypes.Mage;
import com.company.MonsterTypes.SkeletonArcher;
import com.company.MonsterTypes.Slime;
import com.company.MonsterTypes.Zombie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public final class Console {
     /**
     * Clears console "Not working
     */
    public final static void Clear()
    {
        /*try
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
        }*/
        int displaylength = 15;
        for (int i = 0; i < displaylength; i++) {
            Console.Msg("",false);
        }
    }

    /**
     * Write Message in console
     * @param msg The message to be shown
     * @param Wait Shall the console Wait for user to respond.
     * @param Clear Shall the console Clear before view.
     */
    public final static void Msg(String msg, boolean Wait, boolean Clear){
        if (Clear){
            Clear();
        }
        System.out.println(msg);

        if (Wait){
            System.out.println("Please press 'enter' to continue");
            Console.readLine();
        }
    }
    /**
     * Write Message in console
     * @param msg The message to be shown
     * @param Wait Shall the console Wait for user to respond.
     */
    public final static void Msg(String msg, boolean Wait)
    {
        Msg(msg, Wait,false);
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
            ex.printStackTrace();
        }
        return  "";
    }

    /**
     * Interacts with user
     * @param display Type of dialog
     * @return Action required from user
     */
    public final static Object Interact(Dialog display){
        String Message = "";
        switch(display){
            case Level:
                while(true){
                    Console.YarhAsciiArt();
                    Message += "Congratulation you a gain a level.\n";
                    Message += "Choose a stat to improve.\n";
                    Message += "2. Strength\n";
                    Message += "2. DefensePower\n";
                    Message += "3. Intelligence\n";
                    Message += "4. Agility";
                    Console.Msg(Message,false,false);

                    String Response = Console.readLine().toLowerCase();

                    if (Response.equals("1") || Response.equals("2") || Response.equals("3") || Response.equals("4")){
                        return Integer.parseInt(Response);
                    }
                }
            case Move:
                while(true){
                    Console.Msg("Where do you want to go.",false,true);
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
                    Message += "Choose you attack.\n";
                    Message += "1. Melee\n";
                    Message += "2. Ranged\n";
                    Message += "3. Magic\n";
                    Message += "4. Heal";
                    Console.Msg(Message,false,false);

                    String Response = Console.readLine().toLowerCase();

                    if (Response.equals("1") || Response.equals("2") || Response.equals("3") || Response.equals("4")){
                        return Integer.parseInt(Response);
                    }
                }
            case Wellcome:
                Message += "Hello and welcome to the Java Rpg,\n";
                Message += "also known as, The Tower Of Doom.\n";
                Message += "Created by Jesper Baunsgaard and Daniel Jensen";

                Console.Msg(Message,true,true);
                break;
            default:
                break;
        }
        return Message;
    }

    public final static void YarhAsciiArt(){
        String Message ="";
        Message += "    ,    yeah \n";
        Message += "    \\    /  \n";
        Message += "     \\0     \n";
        Message += "      |\\/  \n";
        Message += "      |     \n";
        Message += "     / \\   \n";
        Message += "    /   \\   \n";
        Console.Msg(Message,false,true);
    }

    public final static void BattleAsciiArt(Player player,Monster monster){
        Console.Msg(" Hp: " + player.CurrentHealth + "      Hp: " + monster.CurrentHealth + "\n",false,false);
        String Message ="";
        Message += "  Player      " + monster.getClass().getSimpleName() + "\n";
        if (monster instanceof Slime){
            Message += "             \n";
            Message += "    (}       \n";
            Message += "  \\/|\\__   \n";
            Message += "    |          ----\n";
            Message += "   / \\        | #   \\\n";
            Message += "  /   \\       |______\\ \n";
        }
        else if (monster instanceof Mage) {
            Message += "              \n";
            Message += "    (}        o £ o  \n";
            Message += "  \\/|\\__      |\\O/|\n";
            Message += "    |          \\Y/\n";
            Message += "   / \\         /_\\\n";
            Message += "  /   \\        _W_ \n";
        }
        else if (monster instanceof Zombie){
            Message += "              \n";
            Message += "    (}           o  \n";
            Message += "  \\/|\\__      __/|  \n";
            Message += "    |            |  \n";
            Message += "   / \\          / \\ \n";
            Message += "  /   \\         |  | \n";
        }
        else if (monster instanceof SkeletonArcher){
            Message += "              /\\       \n";
            Message += "    (}       /__\\_{)   \n";
            Message += "  \\/|\\__    |--<<)__\\  \n";
            Message += "    |        \\  /  (   \n";
            Message += "   / \\        \\/  /|   \n";
            Message += "  /   \\           ||   \n";
        }
        Console.Msg(Message,false,true);
        Console.Msg(" Hp: " + player.CurrentHealth + "      Hp: " + monster.CurrentHealth +"\n",false,false);
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
