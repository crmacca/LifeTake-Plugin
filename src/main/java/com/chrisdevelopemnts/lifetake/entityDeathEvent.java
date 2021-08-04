package com.chrisdevelopemnts.lifetake;
//This one randomly gives you a mobs life with a low percentage rate of earning a life from it! If its a player, the game randomly generates a number and if the number is over a specfic amount then the player gets a life!

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Random;


public class entityDeathEvent implements Listener {
    private Plugin plugin = LifeTake.getPlugin(LifeTake.class);
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity().getKiller() != null){
            //We have verified its a player that killed the mob!
            Random rand = new Random();
            if(rand.nextInt(100) >= 90) {
                NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
                PersistentDataContainer data = event.getEntity().getKiller().getPersistentDataContainer(); //Get players data from the dat files stored under the world.
                double playerLives = data.get(namespacedKey, PersistentDataType.DOUBLE);
                double newLives = playerLives + 1; //Add the value
                data.set(namespacedKey, PersistentDataType.DOUBLE, newLives);

                String strMsg = "&c&lYou earned a free life from killing a mob!&r&2 Hover over this for more information!";
                strMsg = ChatColor.translateAlternateColorCodes('&', strMsg);
                TextComponent plrMsg = new TextComponent( strMsg );
                plrMsg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Every time a player kills a mob, a random percentage is generated, there is a %15 percent chance you take the life of the mob, thats why you got a life!").create() ) );
                event.getEntity().getKiller().spigot().sendMessage(plrMsg);
            } else {
        }


        }
    }

}
