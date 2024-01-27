package de.niklas1623.nickconomy.commands;

import de.niklas1623.nickconomy.NickConomy;
import de.niklas1623.nickconomy.api.NickConomyAPI;
import de.niklas1623.nickconomy.handler.BankHandler;
import de.niklas1623.nickconomy.handler.PlayerHandler;
import de.niklas1623.nickconomy.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MoneyCommand implements CommandExecutor {


    public MoneyCommand(NickConomy nickConomy) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("nickconomy")) {
            if (sender.hasPermission("nickconomy.command.money")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        ConfigManager.reloadMessages();
                        ConfigManager.readConfig();
                        sender.sendMessage(ConfigManager.getMsg("Reload"));
                    } else {
                        sender.sendMessage(ConfigManager.getMsg("Help"));
                    }
                }
            } else {
                sender.sendMessage(ConfigManager.getMsg("NoPerm"));
            }
        } else if (cmd.getName().equalsIgnoreCase("nickmoney")) {
            // /nickmoney Spielername
            if (args.length == 0) {
                if (sender instanceof Player) {
                    String bankName = BankHandler.getName(1);
                    double balance = NickConomyAPI.getBalance(((Player) sender).getUniqueId(), bankName);
                    sender.sendMessage(ConfigManager.getMsg("Balance").replaceAll("%money%", NickConomy.format(balance)));
                } else  sender.sendMessage(ConfigManager.getMsg("OnlyPlayer"));
            } else if (args.length == 1) {
                String target = args[0];
                if (PlayerHandler.getPlayerID(target) != 0) {
                    String bankName = BankHandler.getName(1);
                    double balance = NickConomyAPI.getBalance(target, bankName);
                    sender.sendMessage(ConfigManager.getMsg("Balance").replaceAll("%money%", NickConomy.format(balance)));
                } else sender.sendMessage(ConfigManager.getMsg("NoAccount").replaceAll("%name%", target));
            }

        } else if (cmd.getName().equalsIgnoreCase("money")) {
            if (sender.hasPermission("nickconomy.use") || sender.hasPermission("nickconomy.*")) {
                // /money pay NAME BETRAG
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("pay")) {
                        if (sender instanceof Player) {
                            if (sender.hasPermission("nickconomy.pay") || sender.hasPermission("nickconomy.*")) {
                                if (args.length == 3) {
                                    String target = args[1];
                                    if (target != null) {
                                        if (NickConomyAPI.checkIfPlayerExist(target)) {
                                            int targetID = PlayerHandler.getPlayerID(target);
                                            UUID senderUUID = ((Player) sender).getUniqueId();
                                            String st_balance = args[2].replaceAll(",", ".");
                                            if (st_balance.matches("\\d+([.]{1}\\d+)?")) {
                                                double balance = Double.parseDouble(st_balance);
                                                double balance_minus_taxes = NickConomyAPI.addMoney(targetID, "CultBank", balance);
                                                double taxes = balance - balance_minus_taxes;
                                                NickConomyAPI.takeMoney(senderUUID, "CultBank", balance);
                                                sender.sendMessage(ConfigManager.getMsg("MoneyPay").replaceAll("%target%", args[1]).replaceAll("%amount%", NickConomy.format(balance)));
                                                if (Bukkit.getPlayer(target) != null) {
                                                    Player p_target = Bukkit.getPlayer(target);
                                                    p_target.sendMessage(ConfigManager.getMsg("ReceiveMoney").replaceAll("%sender%", sender.getName()).replaceAll("%amount%",
                                                            NickConomy.format(balance)).replaceAll("%taxes%", NickConomy.format(taxes)).replaceAll("%amountminustaxes%",
                                                            NickConomy.format(balance_minus_taxes)));
                                                }
                                            } else sender.sendMessage(ConfigManager.getMsg("WrongAmount"));
                                        } else
                                            sender.sendMessage(ConfigManager.getMsg("NoAccount").replaceAll("%name%", args[1]));
                                    } else sender.sendMessage(ConfigManager.getMsg("NoAccount").replaceAll("%name%", args[1]));
                                } else sender.sendMessage(ConfigManager.getMsg("HowToPay"));
                            } else sender.sendMessage(ConfigManager.getMsg("NoPerm"));
                        } else sender.sendMessage(ConfigManager.getMsg("OnlyPlayer"));
                    } else if (args[0].equalsIgnoreCase("help")) {
                        sender.sendMessage(ConfigManager.getMsg("HowToPay"));
                    } else sender.sendMessage(ConfigManager.getMsg("Help"));
                } else sender.sendMessage(ConfigManager.getMsg("Help"));
            } else sender.sendMessage(ConfigManager.getMsg("NoPerm"));
        }
        return true;
    }
}
