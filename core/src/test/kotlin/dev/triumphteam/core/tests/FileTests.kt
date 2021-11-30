/**
 * MIT License
 *
 * Copyright (c) 2021 Mateus Moreira
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
package dev.triumphteam.core.tests

import dev.triumphteam.core.config.Defaults
import dev.triumphteam.core.config.Messages
import dev.triumphteam.core.locale.Language
import dev.triumphteam.core.locale.Locale
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class FileTests {

    private val resources = Paths.get("src", "test", "resources").toFile()

    //private val config = AbstractConfig(resources)
    private val locale = Locale(resources)

    init {
        //config.create(Settings::class.java)
        locale.setHolder(Messages::class.java).create()
    }

    @Test
    fun `read from config file`() {
        //assertThat(config[Settings.TEST_PROPERTY]).isEqualTo("Default value")
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
        locale.setLocale(Language.PORTUGUESE).create()
        assertThat(locale[Messages.MESSAGE_TEST]).isEqualTo("Mensagem Customizada Em Portugues")
    }

}