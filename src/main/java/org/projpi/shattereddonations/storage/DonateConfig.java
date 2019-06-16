package org.projpi.shattereddonations.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.projpi.shattereddonations.ShatteredDonations;
import org.projpi.shattereddonations.rewards.RewardParser;
import org.projpi.shattereddonations.rewards.DonationReward;

import java.util.*;

/**
 * Manages values in a linked configuration.
 */
public class DonateConfig
{
    private ArrayList<DonationReward> rewards;
    private int totalWeight;
    private static Random random = new Random();

    /**
     * Whether all players should receive the same reward.
     */
    public final boolean sameReward;
    /**
     * Whether the plugin should use titles or messages to communicate.
     */
    public final boolean titles;
    /**
     * The link to donate to the server.
     */
    public final String donateLink;

    /**
     * Builds a representation of a config from a given YamlConfiguration.
     *
     * @param instance The instance of ShatteredDonations. Dependency injection.
     * @param config The configuration to load values from.
     */
    public DonateConfig(ShatteredDonations instance, YamlConfiguration config)
    {
        random = new Random();
        rewards = new ArrayList<>();
        sameReward = config.getBoolean("same-reward", true);
        titles = config.getBoolean("titles", true);
        donateLink = config.getString("donate-link", "change-in-config.yml");

        // We have to mostly write our own parser for a list of unkeyed objects.
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
                if (parser == null)
                {
                    instance.getLogger().warning("Could not parse reward with type '" + type +
                            "' as that type does not exist. Skipping.");
                    continue;
                }
                DonationReward reward = parser.parse(instance, map);
                if (reward == null)
                {
                    continue;
                }
                rewards.add(parser.parse(instance, map));
            }
        }

        instance.getLogger().info("Loaded " + rewards.size() + " rewards with total weight " + totalWeight);
    }

    /**
     * @return A random reward from the loaded rewards.
     */
    public DonationReward getRandomReward()
    {
        int localTotal = random.nextInt(totalWeight) + 1;
        for (DonationReward reward : rewards)
        {
            localTotal -= reward.getWeight();
            if (localTotal <= 0)
            {
                return reward;
            }
        }
        return rewards.get(rewards.size() - 1); // Shouldn't ever be reached, but here just in case. Last item is last.
    }
}
