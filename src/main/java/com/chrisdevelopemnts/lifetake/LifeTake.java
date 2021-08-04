package com.chrisdevelopemnts.lifetake;

import com.chrisdevelopemnts.lifetake.commands.livesCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class LifeTake extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[LifeTake Java] - Loading all events.");
        getServer().getPluginManager().registerEvents(new joinEvent(), this);
        getServer().getPluginManager().registerEvents(new deathEvent(), this);
        getServer().getPluginManager().registerEvents(new entityDeathEvent(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - Loaded all registered event classes!");
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[LifeTake Java] - Loading registered commands.");
        getCommand("lives").setExecutor(new livesCommand());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - Loaded all registered commands!");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - The plugin is now ready for use!");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - The plugin is disabling, player data will be saved with the world.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


}
