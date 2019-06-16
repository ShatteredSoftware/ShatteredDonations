package org.projpi.shattereddonations.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.HashMap;

/**
 * The /donation command. Executes the donation event on the provided player.
 */
public class DonationCommand implements CommandExecutor
{
    private transient final ShatteredDonations instance;

    /**
     * Builds the donation command.
     *
     * @param instance The instance of ShatteredDonations. Dependency injection.
     */
    public DonationCommand(ShatteredDonations instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if(!commandSender.hasPermission("shatteredDonate.donation"))
        {
            instance.getMessenger().sendErrorMessage(commandSender, "no-permission");
            return true;
        }
        if(args.length != 1)
        {
            HashMap<String, String> msgArgs = new HashMap<>();
            msgArgs.put("argc", String.valueOf(args.length));
            msgArgs.put("reqc", "1");
            instance.getMessenger().sendErrorMessage(commandSender, "not-enough-args", msgArgs);
            return true;
        }
        Player p = Bukkit.getServer().getPlayer(args[0]);
        if(p == null)
        {
            instance.getMessenger().sendErrorMessage(commandSender, "player-not-found");
            return true;
        }
        instance.doRandomReward(p);
        return true;
    }
}
