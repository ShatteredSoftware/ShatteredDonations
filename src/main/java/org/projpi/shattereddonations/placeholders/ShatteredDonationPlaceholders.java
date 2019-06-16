package org.projpi.shattereddonations.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.projpi.shattereddonations.ShatteredDonations;

public class ShatteredDonationPlaceholders extends PlaceholderExpansion
{
    private transient final ShatteredDonations instance;

    /**
     * Builds the placeholder expansion.
     *
     * @param instance The instance of ShatteredDonations. Dependency injection.
     */
    public ShatteredDonationPlaceholders(ShatteredDonations instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean persist()
    {
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier()
    {
        return "shattereddonations";
    }

    @Override
    public String getAuthor()
    {
        return "UberPilot";
    }

    @Override
    public String getVersion()
    {
        return instance.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String params)
    {
        if(player == null)
        {
            return "";
        }

        if(params.equalsIgnoreCase("count"))
        {
            return String.valueOf(instance.getDonationCount(player));
        }

        return "";
    }
}
