package de.niklas1623.nickconomy.utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.database.MySQL;

public class ConfigManager {

    private static File customConfigFile;

    public static FileConfiguration cfg;
    public static FileConfiguration msgConfig;

    public static NickConomy pl = NickConomy.getInstance();


    public static File getConfigFile() {
        return new File("plugins/NickConomy", "config.yml");
    }

    public static void createMessagesConfig(){
        customConfigFile = new File(NickConomy.getPlugin(NickConomy.class).getDataFolder(), "messages.yml");
        if(!customConfigFile.exists()){
            customConfigFile.getParentFile().mkdirs();
            NickConomy.getPlugin(NickConomy.class).saveResource("messages.yml", false);
        }
        msgConfig = new YamlConfiguration();
        try {
            msgConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfigFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile() );
    }

    public static FileConfiguration getMessageFileConfiguration() {
        try {
            msgConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return msgConfig;
    }

    public static void reloadMessages(){
        msgConfig = YamlConfiguration.loadConfiguration(customConfigFile);
    }

    public static void readConfig() {
        cfg = getConfigFileConfiguration();

        MySQL.username = cfg.getString("Database.username");
        MySQL.password = cfg.getString("Database.password");
        MySQL.database = cfg.getString("Database.database");
        MySQL.host = cfg.getString("Database.host");
        MySQL.port = cfg.getString("Database.port");
        MySQL.options = cfg.getString("Database.options");

    }

    public static String getMsg(String path){
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Objects.requireNonNull(msgConfig.getString(path)).replaceAll("%prefix%", NickConomy.prefix)));
    }

}