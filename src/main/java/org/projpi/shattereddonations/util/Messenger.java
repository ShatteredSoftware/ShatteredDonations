package org.projpi.shattereddonations.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;

/**
 * A class to send messages to {@link org.bukkit.command.CommandSender CommandSenders}.
 *
 * @since 1.0.0
 * @author UberPilot
 */
public class Messenger
{
    private transient final ShatteredDonations instance;
    private final Messages messages;

    /**
     * Create a messenger.
     *
     * @param instance The instance of ShatteredDonations. Dependency injection.
     * @param messages MessageSet to link to.
     */
    public Messenger(ShatteredDonations instance, Messages messages)
    {
        this.instance = instance;
        this.messages = messages;
    }

    /**
     * Sends a message without any placeholders.
     *
     * @param sender The sender to send a message to.
     * @param id The id of the message from {@link Messages} to send.
     */
    public void sendMessage(CommandSender sender, String id)
    {
        sendMessage(sender, id, null);
    }


    /**
     * Sends a message with placeholders.
     *
     * @param sender The sender to send a message to.
     * @param id The id of the message from {@link Messages} to send.
     * @param vars The list of placeholders to replace.
     */
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

        if(instance.hasPlaceholders() && sender instanceof Player)
        {
            Player player = (Player) sender;
            message = instance.replace(message, player);
        }
        if(message.length() > 0)
        {
            sender.sendMessage(message);
        }
    }

    /**
     * Sends an error message without any placeholders. Includes a sound effect.
     *
     * @param sender The sender to send a message to.
     * @param id The id of the message from {@link Messages} to send.
     */
    public void sendErrorMessage(CommandSender sender, String id)
    {
        sendErrorMessage(sender, id, null);
    }

    /**
     * Sends an error message with any placeholders. Includes a sound effect.
     *
     * @param sender The sender to send a message to.
     * @param id The id of the message from {@link Messages} to send.
     * @param vars The list of placeholders to replace.
     */
    public void sendErrorMessage(CommandSender sender, String id, Map<String, String> vars)
    {
        sendMessage(sender, id, vars);
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            player.playSound(player.getLocation(), Sound.NOTE_BASS.bukkitSound(), 1, .8f);
        }
    }

    /**
     * Sends an important message without any placeholders. Includes a sound effect.
     *
     * @param sender The sender to send a message to.
     * @param id The id of the message from {@link Messages} to send.
     */
    public void sendImportantMessage(CommandSender sender, String id)
    {
        sendImportantMessage(sender, id, null);
    }

    /**
     * Sends an important message with any placeholders. Includes a sound effect.
     *
     * @param sender The sender to send a message to.
     * @param id The id of the message from {@link Messages} to send.
     * @param vars The list of placeholders to replace.
     */
    public void sendImportantMessage(CommandSender sender, String id, Map<String, String> vars)
    {
        sendMessage(sender, id, vars);
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            player.playSound(player.getLocation(), Sound.ORB_PICKUP.bukkitSound(), 1, .5F);
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () ->
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP.bukkitSound(), 1, .5F), 4L);
        }
    }
}
