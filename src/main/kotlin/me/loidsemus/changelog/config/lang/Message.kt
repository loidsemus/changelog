package me.loidsemus.changelog.config.lang

import me.loidsemus.changelog.extensions.formatColorCodes
import me.loidsemus.changelog.extensions.replaceFast

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