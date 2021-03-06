package me.loidsemus.changelog.extensions

import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor

fun String.replaceFast(search: String, replace: String): String = StringUtils.replace(this, search, replace)

fun String.formatColorCodes() = ChatColor.translateAlternateColorCodes('&', this)