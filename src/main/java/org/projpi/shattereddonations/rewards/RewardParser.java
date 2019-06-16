package org.projpi.shattereddonations.rewards;

import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;

public interface RewardParser
{
    DonationReward parse(ShatteredDonations instance, Map map);

    String getType();
}
