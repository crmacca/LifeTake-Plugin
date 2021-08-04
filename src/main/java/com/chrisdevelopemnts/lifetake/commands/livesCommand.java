package com.chrisdevelopemnts.lifetake.commands;

import com.chrisdevelopemnts.lifetake.LifeTake;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class livesCommand implements CommandExecutor {
    private Plugin plugin = LifeTake.getPlugin(LifeTake.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("lifetake.player")) {
                if (args.length == 0) {
                    NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
                    PersistentDataContainer pData = player.getPersistentDataContainer(); //Get players data from the dat files stored under the world.
                    double pLives = pData.get(namespacedKey, PersistentDataType.DOUBLE);
                    String plrMsg = "&5&lYou&d have &5&l%lives%&r&d lives remaning.";
                    plrMsg = plrMsg.replaceAll("%lives%", String.valueOf(pLives));
                    plrMsg = ChatColor.translateAlternateColorCodes('&', plrMsg);
                    player.sendMessage(plrMsg);

                } else {
                    Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                    if (targetPlayer instanceof Player) {
                        NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
                        PersistentDataContainer pData = targetPlayer.getPersistentDataContainer(); //Get players data from the dat files stored under the world.
                        double pLives = pData.get(namespacedKey, PersistentDataType.DOUBLE);
                        String plrMsg2 = "&5&l%target%&r&d has &5&l%lives%&r&d lives remaning.";
                        plrMsg2 = plrMsg2.replaceAll("%target%", targetPlayer.getName());
                        plrMsg2 = plrMsg2.replaceAll("%lives%", String.valueOf(pLives));
                        plrMsg2 = ChatColor.translateAlternateColorCodes('&', plrMsg2);
                        player.sendMessage(plrMsg2);

                    } else {
                        player.sendMessage(ChatColor.RED + "I was unable to find the user you provided in our database.");
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are not authorised to use that command. If you believe this is an error please contact us on discord.");
            }
        } else { //Console execute command
            if (args.length == 1) {
                Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                if (targetPlayer instanceof Player) {
                    NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
                    PersistentDataContainer pData = targetPlayer.getPersistentDataContainer(); //Get players data from the dat files stored under the world.
                    double pLives = pData.get(namespacedKey, PersistentDataType.DOUBLE);
                    String consoleMsg = "%target% has %lives% lives remaning.";
                    consoleMsg = consoleMsg.replaceAll("%target%", targetPlayer.getName());
                    consoleMsg = consoleMsg.replaceAll("%lives%", String.valueOf(pLives));
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + consoleMsg);

                } else {
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "I was unable to find the user you provided in our database.");
                }
            } else {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Please supply a user to check the lives of!");
            }
        }

        return true;
    }
}
