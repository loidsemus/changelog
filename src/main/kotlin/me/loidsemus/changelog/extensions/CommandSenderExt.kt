package me.loidsemus.changelog.extensions

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

fun CommandSender.sendColoredMessage(message: String) {
    this.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
}