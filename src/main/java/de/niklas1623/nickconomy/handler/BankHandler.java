package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.database.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankHandler {

    public static void createBank(String name, int owner_PID) {
        String createBank = "INSERT INTO player (Name, Owner) VALUES (?,?)";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(createBank);
            ps.setString(1, name);
            ps.setInt(2, owner_PID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static int getBID(String name, int owner) {
        String getBID = "SELECT BID FROM bank WHERE Name = ? AND Owner = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getBID);
            ps.setString(1, name);
            ps.setInt(2, owner);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("BID");
            }
            rs.close();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getName(int BID) {
        String getName = "SELECT Name FROM bank WHERE BID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getName);
            ps.setInt(1, BID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("Name");
            }
            rs.close();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getOwner(int BID) {
        String getOwner = "SELECT Owner FROM bank WHERE BID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getOwner);
            ps.setInt(1, BID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("Owner");
            }
            rs.close();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void changeBankName(String new_name, String name, int BID) {
        String changeName = "UPDATE bank SET Name = ? WHERE Name = ? AND BID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(changeName);
            ps.setString(1, new_name);
            ps.setString(2, name);
            ps.setInt(3, BID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void changeBankOwner(int new_owner_PID, int BID) {
        String changeBankOwner = "UPDATE bank SET Owner = ? WHERE BID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(changeBankOwner);
            ps.setInt(1, new_owner_PID);
            ps.setInt(2, BID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteBank(int BID) {
        String deleteBank = "DELETE FROM bank WHERE BID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(deleteBank);
            ps.setInt(1, BID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
