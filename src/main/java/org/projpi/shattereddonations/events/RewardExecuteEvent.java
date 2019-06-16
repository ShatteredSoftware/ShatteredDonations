package org.projpi.shattereddonations.events;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.projpi.shattereddonations.rewards.DonationReward;

/**
 * Called when a reward is to be executed on a player.
 */
public class RewardExecuteEvent extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private Player target;
    private DonationReward reward;
    private boolean cancelled;

    /**
     * Builds an event called when a reward is to be executed on a player.
     *
     * @param target The player targeted by the reward.
     * @param reward The reward to be executed.
     */
    public RewardExecuteEvent(Player target, DonationReward reward)
    {
        if (target == null)
        {
            throw new IllegalArgumentException("Target cannot be null.");
        }
        if (reward == null)
        {
            throw new IllegalArgumentException("Reward cannot be null.");
        }
        this.target = target;
        this.reward = reward;
    }

    /**
     * @return The player targeted by this reward.
     */
    public Player getTarget()
    {
        return target;
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
