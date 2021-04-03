package com.supercrafter100.punishmentgui.Command;


import com.supercrafter100.punishmentgui.Main;
import com.supercrafter100.punishmentgui.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class PunishGUICommand implements CommandExecutor {

    Main plugin;

    public PunishGUICommand (Main instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission(plugin.getConfig().getString("permissions.command"))) {
            sender.sendMessage(Utils.format(plugin.getConfig().getString("messages.no_permission")));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(Utils.format(plugin.getConfig().getString("messages.main_usage")));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(Utils.format(plugin.getConfig().getString("messages.config_reloaded")));
        } else {
            sender.sendMessage(Utils.format(plugin.getConfig().getString("messages.main_usage")));
        }
        return true;
    }
}
