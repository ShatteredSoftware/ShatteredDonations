package org.projpi.shattereddonations.events;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class ShatteredDonationEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private CommandSender trigger;
    private Player target;
    private List<Player> effected;

    public ShatteredDonationEvent(CommandSender trigger, Player target, List<Player> effected)
    {
        this.trigger = trigger;
        this.target = target;
        this.effected = effected;
    }

    public CommandSender getTrigger()
    {
        return trigger;
    }

    public Player getTarget()
    {
        return target;
    }

    public List<Player> getEffected()
    {
        return effected;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
