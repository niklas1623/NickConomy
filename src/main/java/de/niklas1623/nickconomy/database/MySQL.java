package de.niklas1623.nickconomy.database;

import de.niklas1623.nickconomy.NickConomy;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static String options;
    public static Connection con;

    public static void connect() {
        if (!isConnected()){
            try {
                con = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database + options,
                        username, password);
                Bukkit.getConsoleSender().sendMessage(NickConomy.prefix+ " MySQL Verbindung §aaufgebaut§7!");
            } catch (SQLException exception) {
                exception.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(NickConomy.prefix+ " MySQL Verbindung §cgeschlossen§r!");
            }

        }
    }

    public static void close(){
        if (isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage(NickConomy.prefix+ " MySQL Verbindung §cgeschlossen§r!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static boolean isConnected() {
        return con != null;
    }

    public static void createTable() {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS player (PID INT NOT NULL AUTO_INCREMENT, Name TEXT NOT NULL, UUID TEXT NOT NULL , PRIMARY KEY (PID), UNIQUE (UUID))");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS player_x_bank (PID INT NOT NULL, BID INT NOT NULL, AID INT NOT NULL)");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS bank (BID INT NOT NULL AUTO_INCREMENT, Name VARCHAR(128) NOT NULL, Owner INT NOT NULL, PRIMARY KEY(BID))");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS account (AID INT NOT NULL AUTO_INCREMENT, AtID INT NOT NULL, Balance DOUBLE NOT NULL, PRIMARY KEY(AID))");
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS accounttyp (AtID INT NOT NULL AUTO_INCREMENT, Typ VARCHAR(64) NOT NULL, PRIMARY KEY(AtID))");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
