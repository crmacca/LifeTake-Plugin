package com.chrisdevelopemnts.lifetake;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class LifeTake extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new joinEvent(), this);
        getServer().getPluginManager().registerEvents(new deathEvent(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - The plugin has started up!");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - The plugin is disabling, player data will be saved with the world.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


}
