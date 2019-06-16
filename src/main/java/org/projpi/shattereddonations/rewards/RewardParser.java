package org.projpi.shattereddonations.rewards;

import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;

public interface RewardParser
{
    /**
     * Creates a DonationReward from a Map.
     *
     * @param instance The instance of ShatteredDonations. Dependency injection.
     * @param map The map of values to parse.
     * @return An instance of DonationReward, or <code>null</code> if there was a parsing error.
     */
    DonationReward parse(ShatteredDonations instance, Map map);

    /**
     * @return The 'type' in the config that this should register for.
     */
    String getType();
}
