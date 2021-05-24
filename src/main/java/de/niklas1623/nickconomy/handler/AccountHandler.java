package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.database.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountHandler {

    public static void createAccount(int AtID, double Balance) {
        String createAccount = "INSERT INTO account (AtID, Balance) VALUES (?,?)";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(createAccount);
            ps.setInt(1, AtID);
            ps.setDouble(2, Balance);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static double getBalance(int AID) {
        String getBalance = "SELECT Balance FROM account WHERE AID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getBalance);
            ps.setInt(1, AID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble("Balance");
            }
            rs.close();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0.0;
    }

    public static void setBalance(int AID, double Balance) {
        String setBalance = "UPDATE account SET Balance = ? WHERE AID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(setBalance);
            ps.setDouble(1, Balance);
            ps.setInt(2, AID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteAccount(int AID) {
        String deleteAccount = "DELETE FROM account WHERE AID = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(deleteAccount);
            ps.setInt(1, AID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
