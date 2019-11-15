package me.mattstudios.mattscore;

import me.mattstudios.mattscore.configuration.Config;
import me.mattstudios.mattscore.locale.Locale;
import me.mattstudios.mattscore.locale.LocaleHandler;
import me.mattstudios.mf.base.CommandBase;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MattPlugin extends JavaPlugin {

    private Config config;
    private Locale locale;
    private static LocaleHandler localeHandler;
    private CommandManager commandManager;


    protected abstract void onPluginLoad();

    protected abstract void onPluginEnable();

    protected abstract void onPluginDisable();

    @Override
    public final void onLoad() {
        onPluginLoad();
    }

    @Override
    public final void onEnable() {
        config = new Config(this);

        localeHandler = new LocaleHandler();
        locale = new Locale(this);

        commandManager = new CommandManager(this);

        onPluginEnable();
    }

    @Override
    public final void onDisable() {
        onPluginDisable();
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    protected void registerCommand(CommandBase command) {
        commandManager.register(command);
    }

    protected void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public Locale getLocale() {
        return locale;
    }

    public static LocaleHandler getLocaleHandler() {
        return localeHandler;
    }
}
