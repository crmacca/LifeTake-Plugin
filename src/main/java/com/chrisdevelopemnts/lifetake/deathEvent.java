package com.chrisdevelopemnts.lifetake;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class deathEvent implements Listener {
    private Plugin plugin = LifeTake.getPlugin(LifeTake.class);

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
        PersistentDataContainer data = event.getEntity().getPersistentDataContainer(); //Get players data from the dat files stored under the world.
        double livesLeft = data.get(namespacedKey, PersistentDataType.DOUBLE);

        if(event.getEntity().getKiller() != null){
            PersistentDataContainer killerData = event.getEntity().getKiller().getPersistentDataContainer(); //Get players data from the dat files stored under the world.
            double killerLives = data.get(namespacedKey, PersistentDataType.DOUBLE);


        String deathMessage = "&k--------------------\n&r&c&l%player_died% has been killed by %player_killer%!\n&r&k--------------------";
        deathMessage = ChatColor.translateAlternateColorCodes('&', deathMessage);
        deathMessage.replaceAll("%player_died%", event.getEntity().getName());
        deathMessage.replaceAll("%player_killer%", event.getEntity().getKiller().getName());
        event.setDeathMessage(deathMessage);


        String deathNotification = "&c&lYou were killed by %player_killer%, therefore you have lost a life! You are now at %lives% lives!\nYou can purchase more on our store!";
        deathNotification = ChatColor.translateAlternateColorCodes('&', deathNotification);
        deathNotification.replaceAll("%lives%", String.valueOf(livesLeft));
        deathNotification.replaceAll("%player_killer%", event.getEntity().getKiller().getName());
        event.getEntity().sendMessage(deathNotification);

        //Lives Configuration
            double livesConfig = livesLeft - 1.0;
            data.set(namespacedKey, PersistentDataType.DOUBLE, livesConfig); // Set the death user to 1 less life

            double kLivesConfig = killerLives + 1.0;
            killerData.set(namespacedKey, PersistentDataType.DOUBLE, kLivesConfig);

            String killerNotification = "&2&lYou killed %player_name%! In return, you have been given 1 of their lives!\n&r&4You now have %lives% lives!";
            killerNotification = ChatColor.translateAlternateColorCodes('&', killerNotification);
            killerNotification.replaceAll("%player_name%", event.getEntity().getName());
            killerNotification.replaceAll("%lives%", String.valueOf(killerLives));
            event.getEntity().getKiller().sendMessage(killerNotification);


        } else {
            String otherMessage = "&5%player_died% has died to a %player_killer%!";
            otherMessage = ChatColor.translateAlternateColorCodes('&', otherMessage);
            otherMessage.replaceAll("%player_died%", event.getEntity().getName());
            otherMessage.replaceAll("%player_killer%", String.valueOf(event.getEntity().getLastDamageCause()));
            event.setDeathMessage(otherMessage);
        }

    }

}
