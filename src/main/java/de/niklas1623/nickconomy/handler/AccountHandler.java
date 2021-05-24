package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.database.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountHandler {

    public static int createAccount(int AtID, double Balance) {
        String createAccount = "INSERT INTO account (AtID, Balance) VALUES (?,?)";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(createAccount);
            ps.setInt(1, AtID);
            ps.setDouble(2, Balance);
            ResultSet key = executeStmtWithGeneratedKeys(ps);
            if (key.next()) {
                System.out.println("created account with aid = " + key.getInt(1));
                return key.getInt(1);
            }
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static void createAccountType(String type) {
        String cAT = "INSERT INTO accounttyp (Typ) VALUES (?)";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(cAT);
            ps.setString(1, type);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static ResultSet executeStmtWithGeneratedKeys(PreparedStatement query) {
        try {
            query.executeUpdate();
            return query.getGeneratedKeys();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static double getBalance(int AID) {
        String getBalance = "SELECT Balance FROM account WHERE AID = ?";
        double balance = 0.0;
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getBalance);
            ps.setInt(1, AID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("Balance");
            }
            rs.close();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return balance;
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
