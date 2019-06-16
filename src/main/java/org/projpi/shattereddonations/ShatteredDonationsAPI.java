package org.projpi.shattereddonations;

import org.bukkit.entity.Player;
import org.projpi.shattereddonations.rewards.RewardParser;

public interface ShatteredDonationsAPI
{
    /**
     * Gets a {@link org.projpi.shattereddonations.rewards.RewardParser Parser} from the registry.
     *
     * @param type The type of the parser to get.
     * @return The parser, if it exists, or null otherwise.
     */
    RewardParser getParser(String type);

    /**
     * Registers a {@link org.projpi.shattereddonations.rewards.RewardParser Parser} to the registry, allowing it to be
     * used to build rewards.
     *
     * @param parser The parser to register.
     */
    void registerParser(RewardParser parser);

    /**
     * Removes a parser from the registry.
     *
     * @param type The type of the parser to deregister.
     * @return The parser that was deregisterred if it exists, or null otherwise.
     */
    RewardParser deregisterParser(String type);

    /**
     * Gets a players' donation count.
     *
     * @param player The player whose count to query.
     * @return The player's donation count.
     */
    int getDonationCount(Player player);

    /**
     * Update a player's donation count.
     *
     * @param player The player whose count to update.
     * @param donations The value to update their donations to.
     */
    void setDonationCount(Player player, int donations);

    /**
     * Does a random reward with the given player as the triggerer.
     *
     * @param player The triggerer of the reward.
     */
    void doRandomReward(Player player);

    /**
     * Loads the data associated with a player.
     *
     * @param player The player whose data to save.
     */
    void loadPlayer(Player player);

    /**
     * Saves the data associated with a player.
     *
     * @param player The player whose data to save.
     */
    void savePlayer(Player player);
}
