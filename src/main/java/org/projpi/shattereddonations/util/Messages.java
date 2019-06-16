package org.projpi.shattereddonations.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.util.HashMap;

public class Messages
{
    private HashMap<String, String> messages;

    public Messages(JavaPlugin instance, YamlConfiguration config)
    {
        messages = new HashMap<>();
        YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(
                instance.getResource("defaultMessages.yml")));
        for(String key : config.getKeys(false))
        {
            messages.put(key, ChatColor.translateAlternateColorCodes('&', config.getString(key)));
        }
        for(String key : defaults.getKeys(false))
        {
            messages.putIfAbsent(key, ChatColor.translateAlternateColorCodes('&', defaults.getString(key)));
        }
    }

    public String getMessage(String key)
    {
        return messages.get(key);
    }
}
