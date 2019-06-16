package org.projpi.shattereddonations.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.HashMap;

/**
 * The /donate command. Gives the player the link to the donation site.
 */
public class DonateCommand implements CommandExecutor
{
    private transient final ShatteredDonations instance;

    /**
     * Builds the donate command.
     *
     * @param instance The instance of ShatteredDonations. Dependency injection.
     */
    public DonateCommand(ShatteredDonations instance)
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
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("site", instance.config().donateLink);
        instance.getMessenger().sendMessage(commandSender, "donate", placeholders);

        return true;
    }
}
