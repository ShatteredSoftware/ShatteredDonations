package org.projpi.shattereddonations.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.projpi.shattereddonations.ShatteredDonations;
import org.projpi.shattereddonations.rewards.RewardParser;
import org.projpi.shattereddonations.rewards.DonationReward;

import java.util.*;

public class DonateConfig
{
    private ArrayList<DonationReward> rewards;
    private int totalWeight;
    Random random;

    public final boolean sameReward;
    public final boolean titles;
    public final String donateLink;

    public DonateConfig(ShatteredDonations instance, YamlConfiguration config)
    {
        random = new Random();
        rewards = new ArrayList<>();
        sameReward = config.getBoolean("same-reward", true);
        titles = config.getBoolean("titles", true);
        donateLink = config.getString("donate-link", "change-in-config.yml");

        List list = config.getList("rewards");
        for (Object o : list)
        {
            if (o instanceof Map)
            {
                Map map = (Map) o;
                String type = (String) map.get("type");
                Integer weight = (Integer) map.get("weight");
                totalWeight += weight != null ? weight : 1;
                RewardParser parser = instance.getParser(type.toLowerCase());
                if(parser == null)
                {
                    instance.getLogger().warning("Could not parse reward with type '" + type +
                            "' as that type does not exist. Skipping.");
                    continue;
                }
                DonationReward reward = parser.parse(instance, map);
                if(reward == null)
                {
                    continue;
                }
                rewards.add(parser.parse(instance, map));
            }
        }

        instance.getLogger().info("Loaded " + rewards.size() + " rewards with total weight " + totalWeight);
    }

    public DonationReward getRandomReward()
    {
        int localTotal = random.nextInt(totalWeight) + 1;
        for(DonationReward reward : rewards)
        {
            localTotal -= reward.getWeight();
            if(localTotal <= 0)
            {
                return reward;
            }
        }
        return rewards.get(rewards.size() - 1); // Shouldn't ever be reached, but here just in case. Last item is last.
    }
}
