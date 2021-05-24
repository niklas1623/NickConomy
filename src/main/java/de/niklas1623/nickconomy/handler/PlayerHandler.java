package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.database.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerHandler {


    public static void createPlayer(String name, String uuid) {
        String cp = "INSERT INTO player (Name, UUID) VALUES (?,?)";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(cp);
            ps.setString(1, name);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPlayerID(String uuid) {
        String getPID = "SELECT PID FROM player WHERE UUID = ?";
        int pID = 0;
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getPID);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pID = rs.getInt("PID");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pID;
    }

    public static void updatePlayerName(String name, String uuid) {
        String updateName = "UPDATE player SET Name = ? WHERE UUID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(updateName);
            ps.setString(1, name);
            ps.setString(2, uuid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getName(int PID) {
        String getName = "SELECT Name FROM Player WHERE PID = ?";
        String name = null;
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getName);
            ps.setInt(1, PID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            name = rs.getString("Name");
            }
            ps.close();
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return name;
    }


    public static void createDefaultAccount() {

    }


}
