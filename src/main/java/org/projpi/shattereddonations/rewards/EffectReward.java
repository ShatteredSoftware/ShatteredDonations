package org.projpi.shattereddonations.rewards;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;

/**
 * A reward that gives the player a status effect.
 */
public class EffectReward implements DonationReward
{
    /**
     * The reward parser for this reward type.
     */
    public static final RewardParser parser = new Parser();

    private final String name;
    private final PotionEffect potion;

    /**
     * Builds an EffectReward.
     *
     * @param effect The potion type for the reward to inflict.
     * @param power The power of the effect, starting at 1.
     * @param duration The duration in ticks.
     * @param name The name of the reward.
     */
    public EffectReward(PotionEffectType effect, int power, int duration, String name)
    {
        this.name = name;
        this.potion = new PotionEffect(effect, duration, power);
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void execute(Player player)
    {
        player.addPotionEffect(potion);
    }

    /**
     * Internal parsing class. Parses a EffectReward from a Map.
     */
    private static class Parser implements RewardParser
    {
        @Override
        public DonationReward parse(ShatteredDonations instance, Map map)
        {
            String effect = (String) map.get("effect");
            if(effect == null)
            {
                instance.getLogger().warning("Effect reward attempted to load but was missing required field " +
                        "'effect'. It has been skipped.");
                return null;
            }
            PotionEffectType effectType = PotionEffectType.getByName(effect.toUpperCase());
            if(effectType == null)
            {
                instance.getLogger().warning("Effect reward attempted to load had invalid field " +
                        "'effect'. It has been skipped.");
                return null;
            }
            String name = map.containsKey("name") ? (String) map.get("name") : effectType.getName();
            int power = map.containsKey("power") ? (int) map.get("power") : 1;
            int duration = map.containsKey("duration") ? (int) (20D * (double) map.get("duration")) : 20;

            return new EffectReward(effectType, power - 1, duration, name);
        }

        @Override
        public String getType()
        {
            return "effect";
        }
    }
}
