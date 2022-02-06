/**
 * MIT License
 *
 * Copyright (c) 2021-2022 TriumphTeam
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
package dev.triumphteam.core.locale

/**
 * Enum with all the possible supported [Language]s.
 * ***TODO Need to add more***.
 */
public enum class Language(public val file: String) {

    ENGLISH("en_US"),
    PORTUGUESE("pt_BR"),
    SPANISH("es_ES"),
    FRENCH("fr_FR"),
    GERMAN("de_DE"),
    DUTCH("nl_NL"),
    CHINESE("zh_CN"),
    JAPANESE("jp_JP"),
    KOREAN("ko_KO"),
    RUSSIAN("ru_RU"),
    ROMANIAN("ro_RO"),
    SLOVENIAN("sl_SL")

    // TODO add more

}