package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.NickConomy;
import org.bukkit.Bukkit;

public class EconomyHandler {

    public static void addAccountToPlayer(int pID, int atID, int bID, double balance) {
        int aID = AccountHandler.createAccount(atID, balance);
        BankHandler.playerAccountBankRealisation(pID, bID, aID);
        Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Account has been added to Player: " + PlayerHandler.getName(pID)+ " on the Bank: " + BankHandler.getName(bID));
    }

    public static void addMoney(int pID, int bID, double balance) {
        double old_balance = AccountHandler.getBalance(BankHandler.getAID(pID, bID));
        double new_balance = old_balance + balance;
        AccountHandler.setBalance(BankHandler.getAID(pID, bID), new_balance);
    }

    public static void takeMoney(int pID, int bID, double balance) {
        double old_balance = AccountHandler.getBalance(BankHandler.getAID(pID, bID));
        double new_balance = old_balance - balance;
        AccountHandler.setBalance(BankHandler.getAID(pID, bID), new_balance);
    }

    public static double getBalance(int pID, int bID) {
        return AccountHandler.getBalance(BankHandler.getAID(pID, bID));
    }

}
