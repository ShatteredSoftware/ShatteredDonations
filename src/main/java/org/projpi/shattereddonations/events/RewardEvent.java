package org.projpi.shattereddonations.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.projpi.shattereddonations.rewards.DonationReward;

/**
 * Called when the donation command is used on a player.
 */
public class RewardEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private Player trigger;
    private DonationReward reward;
    private boolean cancelled;

    /**
     * Builds an event to be called when a player triggers a reward event.
     *
     * @param trigger The player who trigged this reward.
     * @param reward  The reward to be executed.
     */
    public RewardEvent(Player trigger, DonationReward reward)
    {
        if (trigger == null)
        {
            throw new IllegalArgumentException("Trigger cannot be null.");
        }
        if (reward == null)
        {
            throw new IllegalArgumentException("Reward cannot be null.");
        }
        this.trigger = trigger;
        this.reward = reward;
    }

    /**
     * @return The player who triggered this reward.
     */
    public Player getTrigger()
    {
        return trigger;
    }

    /**
     * @return The reward to be executed.
     */
    public DonationReward getReward()
    {
        return reward;
    }

    /**
     * Sets the reward to be executed.
     *
     * @param reward The reward to be executed.
     */
    public void setReward(DonationReward reward)
    {
        this.reward = reward;
    }

    /**
     * Sets whether the event is cancelled.
     *
     * @param cancelled Whether the event was cancelled.
     */
    @Override
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    /**
     * @return Whether the event was cancelled.
     */
    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    /**
     * @return The list of handlers.
     */
    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    /**
     * Statically returns the list of handlers.
     *
     * @return The list of handlers.
     */
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
