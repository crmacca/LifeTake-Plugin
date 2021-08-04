package com.chrisdevelopemnts.lifetake;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Bukkit.*;
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
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player p = (Player) event.getEntity();


        NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
        PersistentDataContainer data = event.getEntity().getPersistentDataContainer(); //Get players data from the dat files stored under the world.
        double livesLeft = data.get(namespacedKey, PersistentDataType.DOUBLE);

        if (event.getEntity().getKiller() != null) {
            PersistentDataContainer kData = event.getEntity().getKiller().getPersistentDataContainer(); //Get players data from the dat files stored under the world.
            double killerLives = kData.get(namespacedKey, PersistentDataType.DOUBLE);


            String deathMessage = "-------------------------------------------\n&r&c&l%player_died% has been killed by %player_killer%!\n&r-------------------------------------------";
            deathMessage = deathMessage.replaceAll("%player_died%", event.getEntity().getName());
            deathMessage = deathMessage.replaceAll("%player_killer%", event.getEntity().getKiller().getName());
            deathMessage = ChatColor.translateAlternateColorCodes('&', deathMessage);
            event.setDeathMessage(deathMessage);


            String deathNotification = "&cYou were killed by %player_killer%, therefore you have lost a life!\n&4You are now at %lives% lives!\n&2You can purchase more on our store!";
            deathNotification = deathNotification.replaceAll("%lives%", String.valueOf(livesLeft));
            deathMessage = deathNotification.replaceAll("%player_killer%", event.getEntity().getKiller().getName());
            deathNotification = ChatColor.translateAlternateColorCodes('&', deathNotification);
            event.getEntity().sendMessage(deathNotification);


            //Lives Configuration
            double livesConfig = livesLeft - 1;
            data.set(namespacedKey, PersistentDataType.DOUBLE, livesConfig); // Set the death user to 1 less life

            if (livesLeft <= 0) {
                p.setGameMode(GameMode.SPECTATOR);
                String noLives = "%player_name% has reached 0 lives, they are now in spectator mode!\nWish to bring them back to life, use /donatelive <player>!";
                noLives = noLives.replaceAll("%player_name%", event.getEntity().getName());
                noLives = ChatColor.translateAlternateColorCodes('&', noLives);
                Bukkit.getServer().broadcastMessage(noLives);
            }

            double kLivesNew = killerLives + 1;
            kData.set(namespacedKey, PersistentDataType.DOUBLE, kLivesNew); //Gives the killer 1 more life!


            String killerNotification = "&2&lYou killed %player_name%! In return, you have been given 1 of their lives!\n&r&4You now have %lives% lives!";
            killerNotification = ChatColor.translateAlternateColorCodes('&', killerNotification);
            killerNotification = killerNotification.replaceAll("%player_name%", event.getEntity().getName());
            killerNotification = killerNotification.replaceAll("%lives%", String.valueOf(killerLives));
            event.getEntity().getKiller().sendMessage(killerNotification);


        } else {
            String otherMessage = "&5%player_died% has died not to a player!";
            otherMessage = otherMessage.replaceAll("%player_died%", event.getEntity().getName());
            otherMessage = otherMessage.replaceAll("%player_killer%", String.valueOf(event.getEntity().getLastDamageCause()));
            otherMessage = ChatColor.translateAlternateColorCodes('&', otherMessage);
            event.setDeathMessage(otherMessage);
        }

    }

}
