package me.loidsemus.changelog.commands

import me.loidsemus.changelog.config.lang.LanguageConfig
import me.loidsemus.changelog.extensions.sendColoredMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

abstract class PlayerOnlySubcommand(
    name: String,
    description: String,
    usage: String,
    private val messages: LanguageConfig
) :
    Subcommand(name, description, usage) {

    abstract fun execute(player: Player, args: List<String>)

    override fun execute(sender: CommandSender, args: List<String>) {
        if (sender !is Player) {
            sender.sendColoredMessage(messages.consolesCanNotRun.get())
            return
        }
        execute(sender, args)
    }
}