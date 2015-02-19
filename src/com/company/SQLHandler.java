package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLHandler {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/javarpg";

    private static final String user = "root";
    private static final String password = "";//PLEASE GOD, LET THIS BE MY PASSWORD

    public SQLHandler() {/*
        System.out.println("connecting to db");
        //Connection connection = fetchConnection();
        System.out.println("connected");
        System.out.println("adding in a testcharacter");
        savePlayer(new Arranew Player(), "testsave");
        System.out.println("done adding");
        System.out.println("fetching saves");
        List<Player> players = loadSave("testsave");
        System.out.println("done, fetched " + players.size() + "players");
*/
    }
    private Connection fetchConnection(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("connection failed");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    boolean savePlayer(List<Player> players, String saveName) {
        try {
            Connection conn = fetchConnection();
            for (Character character : players) {
                String sql = "INSERT INTO characters (saveName, level, currenthealth, strength, agility, intelligence, defensepower, experience)" +
                        " VALUES (?,?,?,?,?,?,?,?);";

                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, saveName);
                preparedStatement.setInt(2, character.Level);
                preparedStatement.setInt(3, character.CurrentHealth);
                preparedStatement.setInt(4, character.Strength);
                preparedStatement.setInt(5, character.Agility);
                preparedStatement.setInt(6, character.Intelligence);
                preparedStatement.setInt(7, character.DefensePower);
                preparedStatement.setInt(8, character.Experience);

                preparedStatement.execute();
            }
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    List<Player> loadSave(String saveName){
        ArrayList<Player> returnList = new ArrayList<Player>();

        try {
            Connection conn = fetchConnection();
            String sql = "SELECT COUNT(*) FROM characters WHERE saveName=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, saveName);

            ResultSet result = preparedStatement.executeQuery();

            int amountToFeetch=0;
            while (result.next()){
                amountToFeetch = result.getInt("COUNT(*)");
            }
            if (amountToFeetch > 0){
                sql = "SELECT level, currenthealth, strength, agility, intelligence, defensepower, experience FROM characters WHERE saveName=?";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, saveName);

                result = preparedStatement.executeQuery();
                while (result.next()){
                    Player player = new Player();
                    player.Level = result.getInt("level");
                    player.Strength = result.getInt("strength");
                    player.Agility = result.getInt("agility");
                    player.Intelligence = result.getInt("intelligence");
                    player.DefensePower = result.getInt("defensepower");
                    player.Experience = result.getInt("experience");
                    player.CurrentHealth = result.getInt("currenthealth");

                    returnList.add(player);
                }
            }


            conn.close();
            return  returnList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
