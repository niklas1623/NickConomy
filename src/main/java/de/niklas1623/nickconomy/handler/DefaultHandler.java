package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

public class DefaultHandler {

    private static final String taxAccount_Name = ConfigManager.cfg.getString("Settings.Tax-Account.AccountName");
    private static final String taxAccount_UUID = ConfigManager.cfg.getString("Settings.Tax-Account.AccountUUID");


    public static void createDefaults() {
       //if ()
        PlayerHandler.createPlayer(taxAccount_Name, taxAccount_UUID);
        BankHandler.createBank(ConfigManager.cfg.getString("Settings.DefaultBank.Name"), 0);
        AccountHandler.createAccount(0, 0);
        Bukkit.getLogger().log(Level.INFO, "Tax-Account wurde erstellt mit dem Namen: "+taxAccount_Name+ " und der UUID: "+taxAccount_UUID);

    }

}
