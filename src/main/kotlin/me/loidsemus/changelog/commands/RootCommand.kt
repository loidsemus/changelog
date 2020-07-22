package me.loidsemus.changelog.commands

import me.loidsemus.changelog.ChangelogPlugin
import me.loidsemus.changelog.extensions.sendColoredMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.streams.toList

class RootCommand(private val plugin: ChangelogPlugin, private val name: String) : CommandExecutor {

    private val subcommands = mutableMapOf<String, Subcommand>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty() || args[0].equals("help", true)) {
            showHelp(sender)
            return true
        }

        val commandName = args[0]
        if (subcommands.containsKey(commandName)) {
            subcommands[commandName]!!.execute(sender, args.drop(1))
            return true
        } else {
            showHelp(sender)
        }

        return true
    }

    fun addSubcommand(subcommand: Subcommand) {
        subcommands[subcommand.name] = subcommand
    }

    private fun showHelp(sender: CommandSender) {
        // Don't show player only commands to console
        val commands: List<Subcommand> = if (sender is Player) subcommands.values.toList()
        else subcommands.values.stream().filter {
            it !is PlayerOnlySubcommand
        }.toList()

        sender.sendColoredMessage(
            if (sender is Player)
                plugin.messages.helpHeader.get("command" to name)
            else plugin.messages.helpHeaderConsole.get("command" to name)
        )
        commands.forEach {
            sender.sendColoredMessage(
                plugin.messages.helpEntry.get(
                    "fullCommand" to "$name ${it.name}${if (it.usage.isNotEmpty()) " ${it.usage}" else ""}",
                    "description" to it.description
                )
            )
        }
    }
}