package dev.triumphteam.bukkit.config

import dev.triumphteam.bukkit.locale.Language

enum class Defaults(private val en: String, private val pt: String) {

    MESSAGE_TEST("Custom English Message", "Mensagem Customizada Em Portugues");

    fun getDefaultMessage(): String {
        return when (lang) {
            Language.PORTUGUESE -> pt
            else -> en
        }
    }

    companion object {
        var lang: Language = Language.ENGLISH
    }

}