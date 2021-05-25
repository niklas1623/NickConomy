package de.niklas1623.nickconomy;

import de.niklas1623.nickconomy.database.MySQL;
import de.niklas1623.nickconomy.handler.DefaultHandler;
import de.niklas1623.nickconomy.listeners.JoinListener;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class NickConomy extends JavaPlugin {

    public static String prefix;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigManager.readConfig();
        ConfigManager.createMessagesConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ConfigManager.cfg.getString("Prefix")));
        MySQL.connect();
        MySQL.createTable();
        registerEvents();
        DefaultHandler.createDefaults();
        Bukkit.getConsoleSender().sendMessage(prefix + " Plugin wurde §ageladen§r!");

    }

    @Override
    public void onDisable() {
        MySQL.close();
        Bukkit.getConsoleSender().sendMessage(prefix+ " §cPlugin wurde gestoppt!");
    }

    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }


    public static NickConomy getInstance() {
        return instance;
    }

    public static NickConomy instance;

}
