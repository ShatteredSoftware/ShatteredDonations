package org.projpi.shattereddonations.rewards;

import org.bukkit.entity.Player;

public interface DonationReward
{
    String getName();
    void execute(Player player);
    default int getWeight()
    {
        return 1;
    }
}
