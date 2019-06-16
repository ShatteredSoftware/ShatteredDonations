package org.projpi.shattereddonations.rewards;

import org.bukkit.entity.Player;

public interface DonationReward
{
    /**
     * @return The name of the reward for placeholders.
     */
    String getName();

    /**
     * Executes the reward on the player.
     * @param player The player to execute the reward on.
     */
    void execute(Player player);

    /**
     * @return The weight of the reward.
     */
    default int getWeight()
    {
        return 1;
    }
}
