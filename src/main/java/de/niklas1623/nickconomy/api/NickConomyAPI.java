package de.niklas1623.nickconomy.api;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.handler.AccountHandler;
import de.niklas1623.nickconomy.handler.BankHandler;
import de.niklas1623.nickconomy.handler.PlayerHandler;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;

import java.util.UUID;

public class NickConomyAPI {

    public static int atID;
    public static int pID;
    public static int bID;
    public static int aID;

    public static void addAccountToPlayer(UUID uuid, String accountTyp, String bankName, double balance) {
        atID = AccountHandler.getAccountTypeID(accountTyp);
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = AccountHandler.createAccount(atID, balance);
        BankHandler.playerAccountBankRealisation(pID, bID, aID);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Account has been added to Player: " + PlayerHandler.getName(pID) + " on the Bank: " + BankHandler.getName(bID));
        }
    }

    public static void addMoney(UUID uuid, String bankName, double balance) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        double old_balance = AccountHandler.getBalance(aID);
        double new_balance = old_balance + balance;
        AccountHandler.setBalance(aID, new_balance);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " "+ balance + " has been added to the Player: "+ PlayerHandler.getName(pID)+ " with the Account: " +aID);
        }
    }

    public static void takeMoney(UUID uuid, String bankName, double balance) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        double old_balance = AccountHandler.getBalance(aID);
        double new_balance = old_balance - balance;
        AccountHandler.setBalance(aID, new_balance);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " "+ balance + " has been taken from the Player: "+ PlayerHandler.getName(pID) + " with the Account: " +aID);
        }
    }

    public static double getBalance(UUID uuid, String bankName) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        return AccountHandler.getBalance(aID);
    }

}
