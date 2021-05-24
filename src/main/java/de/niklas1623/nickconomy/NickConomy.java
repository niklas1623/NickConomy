package de.niklas1623.nickconomy;

import de.niklas1623.nickconomy.database.MySQL;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NickConomy extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigManager.readConfig();
        ConfigManager.createMessagesConfig();
        MySQL.connect();
        MySQL.createTable();
        Bukkit.getConsoleSender().sendMessage(prefix + " Plugin wurde §ageladen§r!");

    }

    @Override
    public void onDisable() {
        MySQL.close();
        Bukkit.getConsoleSender().sendMessage(prefix + " §cPlugin wurde gestoppt!");
    }


    public static NickConomy getInstance() {
        return instance;
    }

    public static NickConomy instance;
    public static String prefix = "&2&lNick&a&lConomy &8»&r";
}
