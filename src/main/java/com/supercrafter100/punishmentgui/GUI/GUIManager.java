package com.supercrafter100.punishmentgui.GUI;


import com.supercrafter100.punishmentgui.Main;
import com.supercrafter100.punishmentgui.Utils.Utils;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIManager {

    Main plugin;

    Player issuer;
    OfflinePlayer victim;

    public GUIManager(Player p, OfflinePlayer victim, Main plugin) {
        this.plugin = plugin;
        this.issuer = p;
        this.victim = victim;
    }

    public void showGui() {

        String[] guiSetup = {
            "sssssssss",
            "sosbsysrs",
            "sssssssss"
        };

        InventoryGui gui = new InventoryGui(plugin, null, "Punishments", guiSetup);

        // Filler
        gui.addElement(new StaticGuiElement('s',
                new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15),
                1,
                click -> true,
                ""
        ));

        // Warnings
        gui.addElement(new StaticGuiElement('o',
                new ItemStack(Material.WOOL, 1, (short) 1),
                1,
                click -> {
                    this.showSpecificGui("warn");
                    return true;
                },
                Utils.format("&eWarn"),
                Utils.format("&7Click to open warn punishments")
        ));

        // Mutes
        gui.addElement(new StaticGuiElement('b',
                new ItemStack(Material.WOOL, 1, (short) 11),
                1,
                click -> {
                    this.showSpecificGui("mute");
                    return true;
                },
                Utils.format("&eMute"),
                Utils.format("&7Click to open mute punishments")
        ));

        // Kicks
        gui.addElement(new StaticGuiElement('y',
                new ItemStack(Material.WOOL, 1, (short) 4),
                1,
                click -> {
                    this.showSpecificGui("kick");
                    return true;
                },
                Utils.format("&eKick"),
                Utils.format("&7Click to open kick punishments")
        ));

        // Bans
        gui.addElement(new StaticGuiElement('r',
                new ItemStack(Material.WOOL, 1, (short) 14),
                1,
                click -> {
                    this.showSpecificGui("ban");
                    return true;
                },
                Utils.format("&eBan"),
                Utils.format("&7Click to open ban punishments")
        ));

        gui.show(issuer);
    }

    private void showSpecificGui(String type) {

        Configuration config = plugin.getConfig();

        String[] guiSetup = {
                "sssssssss",
                "sabcdefgs",
                "shijklmns",
                "sopqrtuvs",
                "sssssssss"
        };

        InventoryGui gui = new InventoryGui(plugin, null, type, guiSetup);

        // Filler
        gui.addElement(new StaticGuiElement('s',
                new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15),
                1,
                click -> true,
                ""
        ));

        String module = "modules." + type;

        String[] letters = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "t", "u", "v"
        };

        int numeration = 0;
        for (String key : config.getConfigurationSection(module).getKeys(false)) {

            String modulePrefix = module + "." + key;
            // Main item
            int durability = config.getInt(modulePrefix + ".durability");

            ItemStack item = new ItemStack(Material.getMaterial(config.getString(modulePrefix +  ".material")), durability);

            String name = config.getString(modulePrefix + ".name");
            List<String> lore = config.getStringList(modulePrefix + ".lore");

            String letter = letters[numeration];
            numeration++;

            gui.addElement(new StaticGuiElement(letter.charAt(0),
                    item,
                    1,
                    click -> {
                        this.showOffenses(config.getStringList(modulePrefix + ".offenses"), config.getString(modulePrefix + ".executor"));
                        return true;
                    },
                    name,
                    String.join("\n", lore)
            ));
        }

        gui.show(issuer);
    }

    private void showOffenses(List<String> offensesList, String executor) {

        String[] guiSetup = {
                "sssssssss",
                "sabcdefgs",
                "shijklmns",
                "sopqrtuvs",
                "sssssssss"
        };

        InventoryGui gui = new InventoryGui(plugin, null, "Choose offense", guiSetup);

        String[] letters = {
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "t", "u", "v"
        };

        // Filler
        gui.addElement(new StaticGuiElement('s',
                new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15),
                1,
                click -> true,
                ""
        ));

        int numerator = 0;
        for (String command : offensesList) {

            int offense = numerator + 1;
            int slot = numerator;

            String letter = letters[slot];
            String cmd  = command.replace("{target}", victim.getName());

            gui.addElement(new StaticGuiElement(letter.charAt(0),
                    new ItemStack(Material.BOOK_AND_QUILL),
                    1,
                    click -> {
                        gui.close();
                        if (executor.equalsIgnoreCase("player")) {
                            issuer.performCommand(cmd);
                        } else  if (executor.equalsIgnoreCase("console")) {
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            Bukkit.dispatchCommand(console, cmd);
                        }
                        return true;
                    },
                    "&eOffense #" + offense,
                    " ",
                    "&fClick to punish this user",
                    "&fRuns: /" + cmd,
                    " "
            ));

            numerator++;
        }

        gui.show(issuer);
    }
}
