package me.loidsemus.changelog.commands

import org.bukkit.command.CommandSender

abstract class Subcommand(val name: String, val description: String, val usage: String) {

    abstract fun execute(sender: CommandSender, args: List<String>)

}