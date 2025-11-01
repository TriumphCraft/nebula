/**
 * MIT License
 *
 * Copyright (c) 2021-2023 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.nebula.provider

import dev.triumphteam.nebula.container.Container
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

/**
 * Provides a logger instance for the given container.
 *
 * @param configuration The configuration for the logger provider.
 */
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
        internal lateinit var keyProvider: (Any) -> String
            private set
        internal var loggerMap: MutableMap<String, Logger> = ConcurrentHashMap()
            private set

        public fun default(logger: Logger) {
            defaultLogger = logger
        }

        public fun key(provider: (Any) -> String) {
            keyProvider = provider
        }

        public fun mapImplementation(map: MutableMap<String, Logger>) {
            loggerMap = map
        }
    }

    override fun provide(container: Any?): Logger {
        if (container == null) return defaultLogger
        // Creates the logger key
        val key = keyProvider(container)
        // Returns a cached logger or creates a new one and caches it.
        return loggers[key] ?: LoggerFactory.getLogger(key).also { loggers[key] = it }
    }
}
