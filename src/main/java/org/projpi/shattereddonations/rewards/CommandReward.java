package org.projpi.shattereddonations.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;

public class CommandReward implements DonationReward
{
    private final String command;
    private final String name;
    private transient final ShatteredDonations instance;

    public CommandReward(ShatteredDonations instance, String command, String name)
    {
        this.command = command;
        this.instance = instance;
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void execute(Player player)
    {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), instance.replace(command, player)
                .replaceAll("%username%", player.getName()));
    }

    public static class Parser implements RewardParser
    {
        @Override
        public DonationReward parse(ShatteredDonations instance, Map map)
        {
            String command = (String) map.get("command");
            if(command == null)
            {
                instance.getLogger().warning("Command reward attempted to load but was missing required field " +
                        "'command'. It has been skipped.");
                return null;
            }
            String name = (String) map.get("name");
            if(name == null)
            {
                instance.getLogger().warning("Command reward attempted to load but was missing required field " +
                        "'name'. It has been skipped.");
                return null;
            }
            return new CommandReward(instance, command, name);
        }

        @Override
        public String getType()
        {
            return "command";
        }
    }
}
