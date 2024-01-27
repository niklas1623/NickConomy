package de.niklas1623.nickconomy.api;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.handler.AccountHandler;
import de.niklas1623.nickconomy.handler.BankHandler;
import de.niklas1623.nickconomy.handler.PlayerHandler;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class NickConomyAPI {

    public static int atID;
    public static int pID;
    public static int bID;
    public static int aID;
    public static int aID_taxHolder;

    static String prefix = NickConomy.prefix;

    public static void addAccountToPlayer(UUID uuid, String accountTyp, String bankName, double balance) {
        atID = AccountHandler.getAccountTypeID(accountTyp);
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = AccountHandler.createAccount(atID, balance);
        BankHandler.playerAccountBankRealisation(pID, bID, aID);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " Account has been added to Player: " + PlayerHandler.getName(pID) + " on the Bank: " + BankHandler.getName(bID));
        }
    }

    /**
     * Adds Money too a Account minus the Tax-Amount
     */
    public static double addMoney(UUID uuid, String bankName, double balance) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        double taxesAmount = ConfigManager.cfg.getDouble("Settings.Tax-Account.Tax-Amount");
        double taxes = (balance*(taxesAmount/100));
        double balance_minus_taxes = balance-taxes;
        double old_balance = AccountHandler.getBalance(aID);
        double new_balance = old_balance + balance_minus_taxes;
        AccountHandler.setBalance(aID, new_balance);
        addMoneyTooTaxHolder(taxes);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " " + balance + " has been added to the Player: "+ PlayerHandler.getName(pID)+ " with the Account: " +aID);
        }
        return balance_minus_taxes;
    }

    /**
     * Adds Money too a Account minus the Tax-Amount
     */
    public static double addMoney(int targetID, String bankName, double balance) {
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(targetID, bID);
        double taxesAmount = ConfigManager.cfg.getDouble("Settings.Tax-Account.Tax-Amount");
        double taxes = (balance*(taxesAmount/100));
        double balance_minus_taxes = balance-taxes;
        double old_balance = AccountHandler.getBalance(aID);
        double new_balance = old_balance + balance_minus_taxes;
        AccountHandler.setBalance(aID, new_balance);
        addMoneyTooTaxHolder(taxes);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " " + balance + " has been added to the Player: "+ PlayerHandler.getName(targetID)+ " with the Account: " +aID);
        }
        return balance_minus_taxes;
    }

    public static boolean checkIfPlayerExist(UUID uuid) {
        boolean exist = false;
        if (PlayerHandler.getPlayerID(uuid) > 0) {
            exist = true;
        }
        return exist;
    }

    public static boolean checkIfPlayerExist(String playername) {
        boolean exist = false;
        if (PlayerHandler.getPlayerID(playername) > 0) {
            exist = true;
        }
        return exist;
    }

    public static void addMoneyTooTaxHolder(double taxes){
        pID = PlayerHandler.getPlayerID(UUID.fromString(Objects.requireNonNull(ConfigManager.cfg.getString("Settings.Tax-Account.AccountUUID"))));
        aID_taxHolder = BankHandler.getAID(pID, 1);
        double old_Balance = AccountHandler.getBalance(aID_taxHolder);
        AccountHandler.setBalance(aID_taxHolder, old_Balance+taxes);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " "+ taxes + " has been added to the Tax-Holder");
        }
    }

    public static void setMoney(UUID uuid, String bankName, double balance) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        double old_balance = AccountHandler.getBalance(aID);
        double new_balance = old_balance + balance;
        AccountHandler.setBalance(aID, new_balance);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " "+ balance + " has been set to the Player: "+ PlayerHandler.getName(pID)+ " with the Account: " +aID);
        }
    }

    public static void giveMoney(UUID uuid, String bankName, double balance) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        double old_balance = AccountHandler.getBalance(aID);
        double new_balance = old_balance + balance;
        AccountHandler.setBalance(aID, new_balance);
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " "+ balance + " was given to the Player: "+ PlayerHandler.getName(pID)+ " with the Account: " +aID);
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
            Bukkit.getConsoleSender().sendMessage(prefix + " "+ balance + " has been taken from the Player: "+ PlayerHandler.getName(pID) + " with the Account: " +aID);
        }
    }

    public static double getBalance(UUID uuid, String bankName) {
        pID = PlayerHandler.getPlayerID(uuid);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        return AccountHandler.getBalance(aID);
    }

    public static double getBalance(String playername, String bankName) {
        pID = PlayerHandler.getPlayerID(playername);
        bID = BankHandler.getBID(bankName);
        aID = BankHandler.getAID(pID, bID);
        return AccountHandler.getBalance(aID);
    }

}
