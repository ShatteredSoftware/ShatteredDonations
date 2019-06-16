package org.projpi.shattereddonations.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;

public class Messenger
{
    private transient final ShatteredDonations plugin;
    private final Messages messages;

    /**
     * Create a messenger.
     * @param plugin Plugin to link to.
     * @param messages MessageSet to link to.
     */
    public Messenger(ShatteredDonations plugin, Messages messages)
    {
        this.plugin = plugin;
        this.messages = messages;
    }

    public void sendMessage(CommandSender sender, String id)
    {
        sendMessage(sender, id, null);
    }

    public void sendMessage(CommandSender sender, String id, Map<String, String> vars)
    {
        if(sender == null)
            throw new IllegalArgumentException("Sender cannot be null.");

        String message = messages.getMessage(id);

        if(vars != null)
        {
            for(Map.Entry<String, String> entry : vars.entrySet())
            {
                message = message.replaceAll('%' + entry.getKey() + '%', entry.getValue());
            }
        }

        if(plugin.hasPlaceholders() && sender instanceof Player)
        {
            Player player = (Player) sender;
            message = plugin.replace(message, player);
        }
        if(message.length() > 0)
        {
            sender.sendMessage(message);
        }
    }

    public void sendErrorMessage(CommandSender sender, String id)
    {
        sendErrorMessage(sender, id, null);
    }

    public void sendErrorMessage(CommandSender sender, String id, Map<String, String> vars)
    {
        sendMessage(sender, id, vars);
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), Sound.NOTE_BASS.bukkitSound(), 1, .8f);
        }
    }

    public void sendImportantMessage(CommandSender sender, String id)
    {
        sendImportantMessage(sender, id, null);
    }

    public void sendImportantMessage(CommandSender sender, String id, Map<String, String> vars)
    {
        sendMessage(sender, id, vars);
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            player.playSound(player.getLocation(), Sound.ORB_PICKUP.bukkitSound(), 1, .5F);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP.bukkitSound(), 1, .5F), 4L);
        }
    }
}
