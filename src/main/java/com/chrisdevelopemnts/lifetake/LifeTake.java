package com.chrisdevelopemnts.lifetake;



import com.google.inject.AbstractModule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class LifeTake extends JavaPlugin {

    @Override
    public void onEnable() {
    getServer().getPluginManager().registerEvents(new joinEvent(),this);
        getServer().getPluginManager().registerEvents(new deathEvent(), this);
    getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[LifeTake Java] - The plugin has started up!");


    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }



}
