package de.niklas1623.nickconomy.commands;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.api.NickConomyAPI;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoneyCommand implements CommandExecutor {


    public MoneyCommand(NickConomy nickConomy) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("nickconomy")) {
            if (sender.hasPermission("nickconomy.command.money")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        NickConomy.getInstance().reloadConfig();
                        ConfigManager.readConfig();
                        ConfigManager.reloadMessages();
                        sender.sendMessage(ConfigManager.getMsg("Reload"));
                    } else sender.sendMessage(NickConomy.prefix + " §cFalscher Command!");
                }
            } else sender.sendMessage(ConfigManager.getMsg("NoPerm"));
        }

        return false;
    }
}
