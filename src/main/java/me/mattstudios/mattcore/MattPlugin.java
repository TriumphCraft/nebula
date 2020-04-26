package me.mattstudios.mattcore;

import me.mattstudios.mattcore.configuration.Config;
import me.mattstudios.mattcore.locale.Locale;
import me.mattstudios.mattcore.utils.Task;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.components.CompletionResolver;
import me.mattstudios.mf.base.components.MessageResolver;
import me.mattstudios.mf.base.components.ParameterResolver;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public abstract class MattPlugin extends JavaPlugin {

    // Command manager from MF
    private CommandManager commandManager;

    // Config object for config handling
    private Config config;
    // Locale object for message handling
    private Locale locale;

    /**
     * Used to pass the onLoad to the main plugin
     */
    protected void load() {
    }

    /**
     * Used to pass the onEnable to the main plugin
     */
    protected void enable() {
    }

    /**
     * Used to pass onDisable to the main plugin
     */
    protected void disable() {
    }

    @Override
    public final void onLoad() {
        // Calls the plugin load
        load();
    }

    @Override
    public final void onEnable() {
        Task.init(this);

        config = new Config(this);
        locale = new Locale(this);

        commandManager = new CommandManager(this, true);

        // Calls the plugin enable
        enable();

    }

    @Override
    public final void onDisable() {
        // Calls the plugin disable
        disable();
    }

    /**
     * Gets the config, overridden from the spigot one
     *
     * @return The config
     */
    @Override
    public Config getConfig() {
        return config;
    }

    /**
     * Gets the locale of the plugin
     *
     * @return returns the Locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Gets the command manager if needed
     *
     * @return The command manager
     */
    protected CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Used for registering the plugin commands
     *
     * @param commands The list of commands to register
     */
    protected void registerCommands(final CommandBase... commands) {
        commandManager.register(commands);
    }

    /**
     * Used for registering command completions
     *
     * @param completionId The completion ID
     * @param resolver     The completion resolver
     */
    protected void registerCompletion(final String completionId, final CompletionResolver resolver) {
        commandManager.getCompletionHandler().register(completionId, resolver);
    }

    /**
     * Used for registering command parameters
     *
     * @param clss     The class
     * @param resolver The parameter resolver
     */
    protected void registerParamType(final Class<?> clss, final ParameterResolver resolver) {
        commandManager.getParameterHandler().register(clss, resolver);
    }

    /**
     * Used for registering command messages
     *
     * @param messageId The message ID
     * @param resolver  The message resolver
     */
    protected void registerMessage(final String messageId, final MessageResolver resolver) {
        commandManager.getMessageHandler().register(messageId, resolver);
    }

    /**
     * Used for registering the listeners easily
     *
     * @param listeners The list of listeners to register
     */
    protected void registerListeners(final Listener... listeners) {
        for (final Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

}
