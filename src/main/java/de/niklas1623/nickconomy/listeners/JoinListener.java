package de.niklas1623.nickconomy.listeners;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.api.NickConomyAPI;
import de.niklas1623.nickconomy.handler.PlayerHandler;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        UUID uuid = p.getUniqueId();
        double initialBalance = ConfigManager.cfg.getDouble("Settings.InitialBalance");
        String bankName = ConfigManager.cfg.getString("Settings.DefaultBank.Name");
        if (PlayerHandler.getPlayerID(uuid) <= 0 ) {
            PlayerHandler.createPlayer(name, uuid);
            NickConomyAPI.addAccountToPlayer(uuid, "Default", bankName, initialBalance);
            if (ConfigManager.getDebugMode()) {
                Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Created Player: "+ name + " with the UUID: " + uuid);
            }
        } else {
            PlayerHandler.updatePlayerName(name, uuid);
            if (ConfigManager.getDebugMode()) {
                Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Updated Name for UUID: " + uuid + " to " + name);
            }
        }

        return true;
    }
}
