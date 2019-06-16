package org.projpi.shattereddonations.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.projpi.shattereddonations.ShatteredDonations;

import java.util.Map;
import java.util.Random;

/**
 * A reward that spawns entities around the player.
 */
public class MobReward implements DonationReward
{
    /**
     * The reward parser for this reward type.
     */
    public static final RewardParser parser = new Parser();

    private final EntityType mob;
    private final int radius;
    private final int amount;
    private final String name;
    private static final Random random = new Random();

    /**
     * Builds a MobReward.
     *
     * @param mob The EntityType to spawn.
     * @param radius The radius to spawn the mobs in.
     * @param amount The number of mobs to spawn.
     * @param name The name of the reward.
     */
    public MobReward(EntityType mob, int radius, int amount, String name)
    {
        this.mob = mob;
        this.radius = radius;
        this.amount = amount;
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
        for(int i = 0; i < amount; i++)
        {
            boolean valid = false;
            int counter = 0;
            Block block = player.getWorld().getBlockAt(player.getLocation());
            while(!valid && counter < 10)
            {
                counter++;
                int x = player.getLocation().getBlockX() + random.nextInt(radius);
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ() + random.nextInt(radius);
                block = player.getWorld().getBlockAt(x, y, z);
                if(block.getType() != Material.AIR)
                {
                    continue;
                }
                Block over = block.getRelative(BlockFace.UP);
                Block under = block.getRelative(BlockFace.DOWN);
                if(under.getType().isSolid())
                {
                    valid = true;
                    continue;
                }
                else if(over.getType() == Material.AIR && block.getType().isSolid())
                {
                    block = over;
                    valid = true;
                }
            }
            player.getWorld().spawnEntity(block.getLocation(), mob);
        }
    }

    /**
     * Internal parsing class. Parses a MobReward from a Map.
     */
    private static class Parser implements RewardParser
    {

        @Override
        public DonationReward parse(ShatteredDonations instance, Map map)
        {
            String mob = (String) map.get("mob");
            if(mob == null)
            {
                instance.getLogger().warning("Mob reward attempted to load but was missing required field " +
                        "'mob'. It has been skipped.");
                return null;
            }
            EntityType mobType;
            try
            {
                mobType = EntityType.valueOf(mob.toUpperCase());
            }
            catch (IllegalArgumentException e)
            {
                instance.getLogger().warning("Mob reward attempted to load but had invalid field " +
                        "'mob'. It has been skipped.");
                return null;
            }

            String name = map.containsKey("name") ? (String) map.get("name") : mobType.name();

            int radius = map.containsKey("radius")
                    ? (int) map.get("radius")
                    : 5;

            int amount = map.containsKey("amount")
                    ? (int) map.get("amount")
                    : 2;

            return new MobReward(mobType, radius, amount, name);
        }

        @Override
        public String getType()
        {
            return "mob";
        }
    }
}
