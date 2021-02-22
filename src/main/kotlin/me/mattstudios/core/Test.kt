package me.mattstudios.core

import me.mattstudios.config.SettingsHolder
import me.mattstudios.core.func.Initializer
import me.mattstudios.mf.base.CommandBase
import me.mattstudios.mf.base.CommandManager
import org.bukkit.event.Listener

object Settings : SettingsHolder
object Messages : SettingsHolder
class TestCommand : CommandBase()
class PlayerListener : Listener
object CompletionInit : Initializer<CommandManager> {
    override fun initialize(manager: CommandManager) {

    }

}

class PluginExample : TriumphPlugin() {

    override fun plugin() {

        load {
            // load code
        }

        enable {
            config(Settings)
            locale(Messages)

            commands {
                initialize(CompletionInit)
                add(TestCommand())
            }

            listeners {
                add(PlayerListener())
            }
        }

        disable {
            // disable
        }

    }

}