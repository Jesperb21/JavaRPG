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
     * @param clear Shall the console be cleared before message shows
     */
    public final static void Msg(String msg, boolean clear)
    {
        if (clear){
            Console.Clear();
        }
        System.out.println(msg);
    }

    private static Random ran = new Random();

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
     * Read Console line
     * @return a string with user response.
     */
    public static final String readLine()
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
}
