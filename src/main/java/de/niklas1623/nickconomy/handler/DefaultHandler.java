package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class DefaultHandler {

    private static final String taxAccount_Name = ConfigManager.cfg.getString("Settings.Tax-Account.AccountName");
    private static final UUID taxAccount_UUID = UUID.fromString(Objects.requireNonNull(ConfigManager.cfg.getString("Settings.Tax-Account.AccountUUID")));


    public static void createDefaults() {
        String bankName = ConfigManager.cfg.getString("Settings.DefaultBank.Name");
        if (BankHandler.getBID(bankName) <= 0) {
            BankHandler.createBank(bankName, 0);
        }
        if (AccountHandler.getAccountTypeID("Default") <= 0) {
            AccountHandler.createAccountType("Default");
        }
        if (PlayerHandler.getPlayerID(taxAccount_UUID) <= 0) {
            PlayerHandler.createPlayer(taxAccount_Name, taxAccount_UUID);
            int aID = AccountHandler.createAccount(AccountHandler.getAccountTypeID("Default"), 0);
            BankHandler.playerAccountBankRealisation(PlayerHandler.getPlayerID(taxAccount_UUID), BankHandler.getBID(bankName), aID);
            if (ConfigManager.getDebugMode()) {
                Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Tax-Account wurde erstellt mit dem Namen: " + taxAccount_Name + " und der UUID: " + taxAccount_UUID);
            }
        }
    }

}
