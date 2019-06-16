package org.projpi.shattereddonations.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.projpi.shattereddonations.ShatteredDonations;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DonateData
{
    private transient final ShatteredDonations instance;

    public DonateData(ShatteredDonations instance)
    {
        this.instance = instance;
    }

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

    public void setDonations(UUID uuid, int donations)
    {
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
