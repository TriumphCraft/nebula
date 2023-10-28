package dev.triumphteam.nebula.provider

import dev.triumphteam.nebula.container.Container
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

public class LoggerProvider(configuration: Configuration) : Provider<Logger> {

    /** Factory for this provider. */
    public companion object Factory : ProviderFactory<Container, Logger, Configuration> {

        override val clazz: Class<Logger> = Logger::class.java

        public override fun install(
            container: Container,
            configure: Configuration.() -> Unit,
        ): Provider<Logger> = LoggerProvider(Configuration().apply(configure))
    }

    private val defaultLogger = configuration.defaultLogger
    private val keyProvider = configuration.keyProvider

    // Logger cache.
    private val loggers: MutableMap<String, Logger> = configuration.loggerMap

    public class Configuration {
        internal lateinit var defaultLogger: Logger
            private set
        internal lateinit var keyProvider: (Container) -> String
            private set
        internal var loggerMap: MutableMap<String, Logger> = ConcurrentHashMap()
            private set

        public fun default(logger: Logger) {
            defaultLogger = logger
        }

        public fun key(provider: (Container) -> String) {
            keyProvider = provider
        }

        public fun mapImplementation(map: MutableMap<String, Logger>) {
            loggerMap = map
        }
    }

    override fun provide(container: Container?): Logger {
        if (container == null) return defaultLogger
        // Creates the logger key
        val key = keyProvider(container)
        // Returns cached logger or creates a new one and caches it.
        return loggers[key] ?: LoggerFactory.getLogger(key).also { loggers[key] = it }
    }
}
