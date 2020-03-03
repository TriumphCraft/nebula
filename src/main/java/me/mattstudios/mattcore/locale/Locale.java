package me.mattstudios.mattcore.locale;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.io.File;

import static ch.jalu.configme.SettingsManagerBuilder.withYamlFile;
import static ch.jalu.configme.configurationdata.ConfigurationDataBuilder.createConfiguration;
import static me.mattstudios.mattcore.utils.MessageUtils.color;

@SuppressWarnings("unused")
public class Locale {

    // The plugin's instance
    private final Plugin plugin;
    // The settings holder of the languages
    private SettingsManager settingsManager;

    /**
     * Constructor of the locale
     *
     * @param plugin The plugin instance
     */
    public Locale(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads the messages file
     *
     * @param messageClass The message file class
     * @param fileName     The file name
     */
    public void load(final Class<? extends SettingsHolder> messageClass, final String fileName) {
        settingsManager = withYamlFile(
                new File(plugin.getDataFolder(), "lang/" + fileName + ".yml"))
                .useDefaultMigrationService()
                .configurationData(createConfiguration(messageClass))
                .create();
    }

    /**
     * Gets a message from the file colored
     *
     * @param property The property path for the message
     * @return The message already colored
     */
    public String getMessage(final Property<String> property) {
        return color(settingsManager.getProperty(property));
    }

    /**
     * Gets a message from the file raw
     *
     * @param property The property path for the message
     * @return The message already colored
     */
    public String getMessageRaw(final Property<String> property) {
        return settingsManager.getProperty(property);
    }

    /**
     * Sends a message to the sender from the lang file
     *
     * @param sender   The sender
     * @param property The property
     */
    public void sendMessage(final CommandSender sender, final Property<String> property) {
        sender.sendMessage(color(settingsManager.getProperty(property)));
    }

    /**
     * Sends a message to the sender from the lang file
     *
     * @param property The property
     */
    public void sendMessage(final Property<String> property) {
        Bukkit.getConsoleSender().sendMessage(color(settingsManager.getProperty(property)));
    }

    /**
     * Used to reload the lang file
     */
    public void reload() {
        settingsManager.reload();
    }

}
