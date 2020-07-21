package me.loidsemus.plugin.config.lang

import me.loidsemus.plugin.extensions.formatColorCodes
import me.loidsemus.plugin.extensions.replaceFast

class Message(val text: String) {

    /**
     * Replaces placeholders and formats color codes
     */
    fun get(vararg replacements: Pair<String, String>): String {
        var textToFormat = this.text
        replacements.forEach {
            textToFormat = textToFormat.replaceFast("{" + it.first + "}", it.second)
        }
        return textToFormat.formatColorCodes()
    }

}