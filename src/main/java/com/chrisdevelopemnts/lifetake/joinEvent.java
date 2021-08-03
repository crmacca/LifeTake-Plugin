package com.chrisdevelopemnts.lifetake;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class joinEvent implements Listener {

    private Plugin plugin = LifeTake.getPlugin(LifeTake.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        String joinmsg = "&dWelcome &5&l%player_name% &r&dto the LifeTake SMP!";
        joinmsg = ChatColor.translateAlternateColorCodes('&', joinmsg);
        joinmsg = joinmsg.replaceAll("%player_name%", event.getPlayer().getName());
        //Set join message!
        event.setJoinMessage(joinmsg);

        //Lives setup and viewing


        NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
        PersistentDataContainer data = event.getPlayer().getPersistentDataContainer(); //Get players data from the dat files stored under the world.


        if(!data.has(namespacedKey, PersistentDataType.DOUBLE)){
            double starterLives = 10;
            data.set(namespacedKey, PersistentDataType.DOUBLE, starterLives);
            //Give player 10 starter lives for free
            double LivesLeft = data.get(namespacedKey, PersistentDataType.DOUBLE);
            //Now get the value of their starting lives.

            String firstmsg = "&cHi %player_name%!\n Welcome to the LifeTake SMP! Since this is your first time, you have been awared %player_lives% free lives!\nIf you have any issues, ask on our discord.\nIf you see a user hacking, please use /report <user> <hack (or just say game cheats!)>\n\nCredits to ChrisTalino for programming this and credit to htm11 for giving the concept idea!";
            firstmsg = firstmsg.replaceAll("%player_name%", event.getPlayer().getName());
            firstmsg = firstmsg.replaceAll("%player_lives%", String.valueOf(LivesLeft));
            firstmsg = ChatColor.translateAlternateColorCodes('&', firstmsg);
            event.getPlayer().sendMessage(firstmsg);
        } else {
            //Player has already joined since they have a value under their player data stating lives.
            double LivesLeft = data.get(namespacedKey, PersistentDataType.DOUBLE);
            String privatemsg = "&cHi %player_name%, welcome back!&4&l You have %player_lives% left!";
            privatemsg = privatemsg.replaceAll("%player_name%", event.getPlayer().getName());
            privatemsg = privatemsg.replaceAll("%player_lives%", String.valueOf(LivesLeft));
            privatemsg = ChatColor.translateAlternateColorCodes('&', privatemsg);
            event.getPlayer().sendMessage(privatemsg);
        }

    }
}
