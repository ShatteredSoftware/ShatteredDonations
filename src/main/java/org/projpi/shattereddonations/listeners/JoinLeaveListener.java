package org.projpi.shattereddonations.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.projpi.shattereddonations.ShatteredDonationsAPI;

public class JoinLeaveListener implements Listener
{
    private transient final ShatteredDonationsAPI instance;

    public JoinLeaveListener(ShatteredDonationsAPI instance)
    {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        instance.loadPlayer(p);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        instance.savePlayer(p);
    }
}
