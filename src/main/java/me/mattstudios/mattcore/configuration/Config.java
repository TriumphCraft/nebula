package me.mattstudios.mattcore.configuration;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

import static ch.jalu.configme.SettingsManagerBuilder.withYamlFile;
import static ch.jalu.configme.configurationdata.ConfigurationDataBuilder.createConfiguration;

@SuppressWarnings("unused")
public final class Config extends YamlConfiguration {

    // The plugin's instance
    private final Plugin plugin;
    // The settings holder of the config
    private SettingsManager settingsManager;

    /**
     * Main constructor of config
     *
     * @param plugin The plugin instance
     */
    public Config(final Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads the config file selected
     *
     * @param configClass The config file class
     */
    public void load(final Class<? extends SettingsHolder> configClass) {
        settingsManager = withYamlFile(
                new File(plugin.getDataFolder(), "config.yml"), YamlFileResourceOptions.builder().indentationSize(2).build())
                .useDefaultMigrationService()
                .configurationData(createConfiguration(configClass))
                .create();
    }

    /**
     * Gets a property
     *
     * @param property The property to get
     * @return The type of property from the config
     */
    public <T> T get(final Property<T> property) {
        return settingsManager.getProperty(property);
    }

    /**
     * Reloads the config
     */
    public void reload() {
        settingsManager.reload();
    }
}
