package de.niklas1623.nickconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("nickmoney")) {
            if (sender.hasPermission("nickconomy.command.money")) {
                if (args.length == 0) {

                }
            }
        }

        return false;
    }
}
