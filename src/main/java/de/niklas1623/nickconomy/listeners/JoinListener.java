package de.niklas1623.nickconomy.listeners;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.handler.AccountHandler;
import de.niklas1623.nickconomy.handler.BankHandler;
import de.niklas1623.nickconomy.handler.PlayerHandler;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        String uuid = p.getUniqueId().toString();
        if (PlayerHandler.getPlayerID(uuid) <= 0 ) {
            PlayerHandler.createPlayer(name, uuid);
            Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Created Player: "+ name + " with the UUID: " + uuid);
            int aID = AccountHandler.createAccount(0, ConfigManager.cfg.getDouble("Settings.InitialBalance"));
            BankHandler.playerAccountBankRealisation(PlayerHandler.getPlayerID(uuid), BankHandler.getBID(ConfigManager.cfg.getString("Settings.DefaultBank.Name")), aID);
        } else {
            PlayerHandler.updatePlayerName(name, uuid);
            Bukkit.getConsoleSender().sendMessage(NickConomy.prefix + " Updated Name for UUID: "+ uuid + " to " + name);
        }

        return true;
    }
}
