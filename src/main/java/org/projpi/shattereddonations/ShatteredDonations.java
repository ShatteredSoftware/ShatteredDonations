package org.projpi.shattereddonations;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.projpi.shattereddonations.commands.DonateCommand;
import org.projpi.shattereddonations.commands.DonationCommand;
import org.projpi.shattereddonations.listeners.JoinLeaveListener;
import org.projpi.shattereddonations.placeholders.ShatteredDonationPlaceholders;
import org.projpi.shattereddonations.rewards.*;
import org.projpi.shattereddonations.events.*;
import org.projpi.shattereddonations.storage.DonateConfig;
import org.projpi.shattereddonations.storage.DonateData;
import org.projpi.shattereddonations.storage.DonateLoader;
import org.projpi.shattereddonations.util.Messages;
import org.projpi.shattereddonations.util.Messenger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Base class for ShatteredDonations.
 *
 * @see ShatteredDonationsAPI
 */
public class ShatteredDonations extends JavaPlugin implements ShatteredDonationsAPI
{
    private HashMap<String, RewardParser> parsers;
    private HashMap<UUID, Integer> donationValues;

    private DonateLoader loader;

    private DonateConfig config;
    private DonateData data;
    private Messenger messenger;
    private boolean papi = false;
    private Messages messages;

    public ShatteredDonations()
    {
        this.parsers = new HashMap<>();
        donationValues = new HashMap<>();
        loader = new DonateLoader(this);
    }

    @Override
    public void onEnable()
    {
        Metrics metrics = new Metrics(this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            new ShatteredDonationPlaceholders(this).register();
            papi = true;
        } else {
            getLogger().info("Could not find PlaceholderAPI! Placeholders will not be parsed.");
        }

        registerParser(EffectReward.parser);
        registerParser(CommandReward.parser);
        registerParser(StatReward.parser);
        registerParser(MobReward.parser);

        loader.load();
        Messages messages = loader.getMessages();
        this.messages = messages;
        messenger = new Messenger(this, messages);
        config = loader.getConfig();
        data = new DonateData(this);
        for(Player p : getServer().getOnlinePlayers())
        {
            donationValues.put(p.getUniqueId(), data.getDonations(p.getUniqueId()));
        }
        this.getCommand("donate").setExecutor(new DonateCommand(this));
        this.getCommand("donation").setExecutor(new DonationCommand(this));
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
    }

    @Override
    public void onDisable()
    {
        for(Map.Entry<UUID, Integer> entry : donationValues.entrySet())
        {
            if(entry.getValue() > 0)
            {
                data.setDonations(entry.getKey(), entry.getValue());
            }
        }
    }

    public void doRandomReward(Player player)
    {
        setDonationCount(player, getDonationCount(player) + 1);
        DonationReward reward = config.getRandomReward();
        HashMap<String, String> args = new HashMap<>();
        args.put("player", player.getDisplayName());
        args.put("reward", reward.getName());
        RewardEvent rewardEvent = new RewardEvent(player, reward);
        getServer().getPluginManager().callEvent(rewardEvent);
        if(rewardEvent.isCancelled())
        {
            return;
        }
        reward = rewardEvent.getReward();
        for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            if(p.hasPermission("shatteredDonations.exempt"))
            {
                continue;
            }
            if(!config.sameReward)
            {
                reward = config.getRandomReward();
                args.put("reward", reward.getName());
            }
            RewardExecuteEvent rewardExecuteEvent = new RewardExecuteEvent(p, reward);
            getServer().getPluginManager().callEvent(rewardExecuteEvent);
            if(rewardExecuteEvent.isCancelled())
            {
                continue;
            }
            reward = rewardExecuteEvent.getReward();
            reward.execute(p);
            if(config.titles)
            {
                p.sendTitle(
                        replace(messages.getMessage("reward-main").replaceAll("%player%", player.getDisplayName()), p),
                        replace(messages.getMessage("reward-sub").replaceAll("%reward%", reward.getName()), p),
                        10, 70, 20);
            }
            else
            {
                messenger.sendMessage(p, "reward-main", args);
                messenger.sendMessage(p, "reward-sub", args);
            }
        }
    }

    @Override
    public RewardParser getParser(String type)
    {
        return parsers.get(type);
    }

    @Override
    public void registerParser(RewardParser parser)
    {
        parsers.putIfAbsent(parser.getType(), parser);
    }

    @Override
    public RewardParser deregisterParser(String type)
    {
        return parsers.remove(type);
    }

    @Override
    public int getDonationCount(Player player)
    {
        return donationValues.get(player.getUniqueId());
    }

    @Override
    public void setDonationCount(Player player, int donations)
    {
        donationValues.put(player.getUniqueId(), donations);
    }


    @Override
    public void loadPlayer(Player player)
    {
        donationValues.put(player.getUniqueId(), data.getDonations(player.getUniqueId()));
    }

    @Override
    public void savePlayer(Player player)
    {
        data.setDonations(player.getUniqueId(), donationValues.get(player.getUniqueId()));
    }

    /**
     * Replaces placeholders, with or without PlaceholderAPI.
     *
     * @param str The string, including placeholders, to replace.
     * @param player The player to set the placeholders to.
     * @return The string, with placeholders changed.
     */
    public String replace(String str, Player player)
    {
        if(!papi)
        {
            return ChatColor.translateAlternateColorCodes('&', str.replaceAll("%username%", player.getName()));
        }

        return PlaceholderAPI.setPlaceholders(player, str);
    }

    /**
     * @return The config associated with this plugin.
     */
    public DonateConfig config()
    {
        return config;
    }

    /**
     * @return Whether or not this plugin has found PlaceholderAPI.
     */
    public boolean hasPlaceholders()
    {
        return papi;
    }

    /**
     * @return The messenger associated with this plugin.
     */
    public Messenger getMessenger()
    {
        return messenger;
    }
}
