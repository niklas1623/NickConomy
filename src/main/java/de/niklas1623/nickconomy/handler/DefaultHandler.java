package de.niklas1623.nickconomy.handler;

import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class DefaultHandler {

    private static final String serverAccount_Name = ConfigManager.cfg.getString("Settings.Tax-Account.AccountName");
    private static final String serverAccount_UUID = ConfigManager.cfg.getString("Settings.Tax-Account.AccountUUID");


    public static void createDefaults() {
       //if ()
        PlayerHandler.createPlayer(serverAccount_Name, serverAccount_UUID);
        Bukkit.getLogger().log(Level.INFO, "Tax-Account wurde erstellt mit dem Namen: "+serverAccount_Name+ " und der UUID: "+serverAccount_UUID);
    }

}
