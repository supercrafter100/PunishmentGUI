package com.supercrafter100.punishmentgui;

import com.supercrafter100.punishmentgui.Command.PunishCommand;
import com.supercrafter100.punishmentgui.Command.PunishGUICommand;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        // Command
        this.getCommand("epunish").setExecutor(new PunishCommand(this));
        this.getCommand("punishgui").setExecutor(new PunishGUICommand(this));

        // Config
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
