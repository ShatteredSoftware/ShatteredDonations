package org.projpi.shattereddonations.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.projpi.shattereddonations.ShatteredDonations;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Manages a data folder for simple player data.
 */
public class DonateData
{
    private transient final ShatteredDonations instance;

    /**
     * @param instance The instance of ShatteredDonations. Dependency injection.
     */
    public DonateData(ShatteredDonations instance)
    {
        this.instance = instance;
    }

    /**
     * Gets the donation count lazily for the given UUID.
     *
     * @param uuid The UUID to lookup.
     * @return The donations from a file, if it exists, 0 otherwise.
     */
    public int getDonations(UUID uuid)
    {
        File f = new File(instance.getDataFolder(), "data" + File.separator + uuid.toString());
        if(!f.exists())
        {
            return 0;
        }

        YamlConfiguration data = YamlConfiguration.loadConfiguration(f);
        return data.getInt("donations", 0);
    }


    /**
     * Sets the donation count lazily for the given UUID.
     *
     * @param uuid The UUID to lookup.
     * @param donations The number of donations to update.
     */
    public void setDonations(UUID uuid, int donations)
    {
        if (donations == 0)
        {
            return;
        }
        File f = new File(instance.getDataFolder(), "data" + File.separator + uuid.toString());

        YamlConfiguration data = YamlConfiguration.loadConfiguration(f);
        data.set("donations", donations);
        try
        {
            data.save(f);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
