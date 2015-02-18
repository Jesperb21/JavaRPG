package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLHandler {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/javarpg";

    private static final String user = "root";
    private static final String password = "";//PLEASE GOD, LET THIS BE MY PASSWORD

    public SQLHandler() {
        System.out.println("connecting to db");
        //Connection connection = fetchConnection();
        System.out.println("connected");
        System.out.println("adding in a testcharacter");
        savePlayer(new Player());
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
    private boolean savePlayer(Character character) {
        try {

            Connection conn = fetchConnection();
            String sql = "INSERT INTO characters (level, currenthealth, strength, agility, intelligence, defensepower, experience)" +
                    " VALUES (?,?,?,?,?,?,?);";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, character.Level);
            preparedStatement.setInt(2, character.CurrentHealth);
            preparedStatement.setInt(3, character.Strength);
            preparedStatement.setInt(4, character.Agility);
            preparedStatement.setInt(5, character.Intelligence);
            preparedStatement.setInt(6, character.DefensePower);
            preparedStatement.setInt(7, character.Experience);

            preparedStatement.execute();

            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    private boolean loadPlayer(){
        try {
            Connection conn = fetchConnection();
            String sql = "SELECT * FROM characters";




            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
