package org.projpi.shattereddonations.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.projpi.shattereddonations.ShatteredDonations;
import org.projpi.shattereddonations.util.Messages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class DonateLoader
{
    private final ShatteredDonations instance;
    private DonateConfig config;
    private Messages messages;

    public DonateLoader(ShatteredDonations instance)
    {
        this.instance = instance;
    }

    public void load()
    {
        if(!instance.getDataFolder().exists())
        {
            instance.getDataFolder().mkdirs();
        }
        File config = new File(instance.getDataFolder(), "config.yml");
        if(!config.exists())
        {
            try
            {
                Files.copy(instance.getResource("config.yml"), config.toPath());
            }
            catch (IOException e)
            {
                instance.getLogger().log(Level.WARNING,
                        "Failed to copy config.yml from the jar. Using default values.", e);
            }
        }
        this.config = new DonateConfig(instance, YamlConfiguration.loadConfiguration(config));
        File messages = new File(instance.getDataFolder(), "messages.yml");
        if(!messages.exists())
        {
            try
            {
                Files.copy(instance.getResource("defaultMessages.yml"), messages.toPath());
            }
            catch (IOException e)
            {
                instance.getLogger().log(Level.WARNING,
                        "Failed to copy messages.yml from the jar. Using default values.", e);
            }
        }
        this.messages = new Messages(instance, YamlConfiguration.loadConfiguration(config));
        new File(instance.getDataFolder(), "data").mkdirs();
    }

    public Messages getMessages()
    {
        return messages;
    }

    public DonateConfig getConfig()
    {
        return config;
    }
}
