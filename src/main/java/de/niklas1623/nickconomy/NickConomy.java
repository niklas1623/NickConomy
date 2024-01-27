package de.niklas1623.nickconomy;


import de.niklas1623.nickconomy.commands.MoneyCommand;
import de.niklas1623.nickconomy.database.MySQL;
import de.niklas1623.nickconomy.handler.DefaultHandler;
import de.niklas1623.nickconomy.listeners.JoinListener;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
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
        registerCommands();
        DefaultHandler.createDefaults();
        Bukkit.getConsoleSender().sendMessage(prefix + " Plugin wurde §ageladen§r!");
        if (ConfigManager.getDebugMode()) {
            Bukkit.getConsoleSender().sendMessage(prefix + " Debugmode is on!");
        }

    }

    @Override
    public void onDisable() {
        MySQL.close();
        Bukkit.getConsoleSender().sendMessage(prefix+ " §cPlugin wurde gestoppt!");
    }

    private void registerCommands() {
        MoneyCommand moneyCommand = new MoneyCommand(this);
        getCommand("nickconomy").setExecutor(moneyCommand);
        getCommand("nickmoney").setExecutor(moneyCommand);
        getCommand("money").setExecutor(moneyCommand);
    }

    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }


    public static NickConomy getInstance() {
        return instance;
    }

    public static String format(double amount) {
        String pattern = "#,##0.00";
        Locale de_locale = new Locale("de", "DE");
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(de_locale);
        formatter.applyPattern(pattern);
        String formatted = formatter.format(amount);

        if (formatted.endsWith(",")) {
            formatted = formatted.substring(0, formatted.length() - 1);
        }

        return formatted;
    }

    public static NickConomy instance;

}
