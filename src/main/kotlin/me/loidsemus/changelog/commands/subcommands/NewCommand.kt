package me.loidsemus.changelog.commands.subcommands

import me.loidsemus.changelog.commands.Subcommand
import org.bukkit.command.CommandSender

class NewCommand : Subcommand("new", "Create a new changelog", "<title>") {

    override fun execute(sender: CommandSender, args: List<String>) {
        sender.sendMessage("Hello!")
        args.forEach {
            sender.sendMessage(it)
        }
    }
}

