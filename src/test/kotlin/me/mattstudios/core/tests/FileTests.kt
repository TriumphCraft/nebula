package me.mattstudios.core.tests

import me.mattstudios.core.config.Defaults
import me.mattstudios.core.config.Messages
import me.mattstudios.core.config.Settings
import me.mattstudios.core.configuration.Config
import me.mattstudios.core.locale.Language
import me.mattstudios.core.locale.Locale
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class FileTests {

    private val resources = Paths.get("src", "test", "resources").toFile()
    private val config = Config(resources)
    private val locale = Locale(resources)

    init {
        config.create(Settings::class.java)
        locale.setHolder(Messages::class.java).setLocale(Language.ENGLISH)
    }

    @Test
    fun `read from config file`() {
        assertThat(config[Settings.TEST_PROPERTY]).isEqualTo("Default value")
    }

    @Test
    fun `test language file`() {
        assertThat(locale[Messages.MESSAGE_TEST]).isEqualTo("Custom English Message")
    }

    @Test
    fun `change language`() {
        // This setting is for testing only, would be done differently for production
        Defaults.lang = Language.PORTUGUESE

        // Changes locale language
        locale.setLocale(Language.PORTUGUESE)
        assertThat(locale[Messages.MESSAGE_TEST]).isEqualTo("Mensagem Customizada Em Portugues")
    }

}