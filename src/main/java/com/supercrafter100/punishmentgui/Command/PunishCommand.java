package com.supercrafter100.punishmentgui.Command;

import com.supercrafter100.punishmentgui.GUI.GUIManager;
import com.supercrafter100.punishmentgui.Main;
import com.supercrafter100.punishmentgui.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class PunishCommand implements CommandExecutor {

    Main plugin;

    public PunishCommand (Main instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.format(plugin.getConfig().getString("messages.no_console")));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission(plugin.getConfig().getString("permissions.command"))) {
            p.sendMessage(Utils.format(plugin.getConfig().getString("messages.no_permission")));
            return true;
        }

        if (args.length < 1) {
            p.sendMessage(Utils.format(plugin.getConfig().getString("messages.usage")));
            return true;
        }

        OfflinePlayer offender = Bukkit.getServer().getPlayer(args[0]);
        if (offender == null) {
            p.sendMessage(Utils.format(plugin.getConfig().getString("messages.invalid_player")));
            return true;
        }

        new GUIManager(p, offender, plugin).showGui();

        return true;
    }
}
