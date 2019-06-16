package org.projpi.shattereddonations;

import org.bukkit.entity.Player;
import org.projpi.shattereddonations.rewards.RewardParser;

public interface ShatteredDonationsAPI
{
    RewardParser getParser(String type);
    void registerParser(RewardParser parser);
    RewardParser deregisterParser(String type);
    int getDonationCount(Player player);
    void setDonationCount(Player player, int donations);

    void loadPlayer(Player p);
    void savePlayer(Player p);
}
