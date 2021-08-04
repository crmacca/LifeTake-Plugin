package com.chrisdevelopemnts.lifetake.commands;

import com.chrisdevelopemnts.lifetake.LifeTake;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class donateLivesCommand implements CommandExecutor {
    private Plugin plugin = LifeTake.getPlugin(LifeTake.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("lifetake.player")) {
                Player targetPlayer = Bukkit.getPlayerExact(args[0]);
                if (targetPlayer instanceof Player) {
                    if (targetPlayer != player) {
                        NamespacedKey namespacedKey = new NamespacedKey(plugin, "lives"); //Uses the minecraft player data to store lives
                        PersistentDataContainer pData = player.getPersistentDataContainer(); //Get players data from the dat files stored under the world.
                        PersistentDataContainer tData = targetPlayer.getPersistentDataContainer();
                        double playerData = pData.get(namespacedKey, PersistentDataType.DOUBLE);
                        double targetData = tData.get(namespacedKey, PersistentDataType.DOUBLE);
                        if (playerData > 5) {
                            double targetSetTo = targetData + 1;
                            tData.set(namespacedKey, PersistentDataType.DOUBLE, targetSetTo);
                            double playerSetTo = playerData - 1;
                            pData.set(namespacedKey, PersistentDataType.DOUBLE, playerSetTo);

                            String plrMsg2 = "&5&l%target%&r&d has been sent a life!&d You are now at &5&l%lives%&r&d lives remaining!";
                            plrMsg2 = plrMsg2.replaceAll("%target%", targetPlayer.getName());
                            plrMsg2 = plrMsg2.replaceAll("%lives%", String.valueOf(playerData));
                            plrMsg2 = ChatColor.translateAlternateColorCodes('&', plrMsg2);
                            player.sendMessage(plrMsg2);

                            String plrMsg = "&5&l%target%&r&d has sent you a life!&d You are now at &5&l%lives%&r&d lives remaining!";
                            plrMsg = plrMsg.replaceAll("%target%", player.getName());
                            plrMsg = plrMsg2.replaceAll("%lives%", String.valueOf(targetData));
                            plrMsg = ChatColor.translateAlternateColorCodes('&', plrMsg);
                            targetPlayer.sendMessage(plrMsg);
                            if (targetData >= 1) {
                                targetPlayer.setGameMode(GameMode.SURVIVAL);
                            }

                        } else {
                            player.sendMessage(ChatColor.RED + "You cannot send any lives, you need a minimum of 5 lives.");
                        }

                    } else {
                        player.sendMessage(ChatColor.RED + "You can't donate a life to yourself!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "I was unable to find the user you provided in our database.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are not authorised to use that command. If you believe this is an error please contact us on discord.");
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage("This command can only be executed by a player, did you mean /setlives?");

        }
        return true;
    }
}

