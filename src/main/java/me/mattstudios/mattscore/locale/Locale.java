package me.mattstudios.mattscore.locale;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static ch.jalu.configme.SettingsManagerBuilder.withYamlFile;
import static ch.jalu.configme.configurationdata.ConfigurationDataBuilder.createConfiguration;

public class Locale {

    private JavaPlugin plugin;
    private SettingsManager settingsManager;

    public Locale(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load(Class messageClass, Locales locale) {
        settingsManager = withYamlFile(
                new File(plugin.getDataFolder(), "lang/" + locale.getFileName()))
                .useDefaultMigrationService()
                .configurationData(createConfiguration(messageClass))
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
