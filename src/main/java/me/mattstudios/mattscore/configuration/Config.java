package me.mattstudios.mattscore.configuration;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static ch.jalu.configme.SettingsManagerBuilder.withYamlFile;
import static ch.jalu.configme.configurationdata.ConfigurationDataBuilder.createConfiguration;

public final class Config extends YamlConfiguration {

    private JavaPlugin plugin;
    private SettingsManager settingsManager;

    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load(Class configClass) {
        settingsManager = withYamlFile(
                new File(plugin.getDataFolder(), "config.yml"))
                .useDefaultMigrationService()
                .configurationData(createConfiguration(configClass))
                .create();
    }

    @SuppressWarnings("unchecked")
    public Object get(Property property) {
        return settingsManager.getProperty(property);
    }

    public void reload() {
        settingsManager.reload();
    }
}
