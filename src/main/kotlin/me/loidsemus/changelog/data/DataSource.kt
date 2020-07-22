package me.loidsemus.changelog.data

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.concurrent.CompletableFuture

abstract class DataSource {

    abstract fun getChangelogs(since: Date): List<Changelog>

    fun getChangelogsAsync(plugin: Plugin, since: Date): CompletableFuture<List<Changelog>> {
        val future = CompletableFuture<List<Changelog>>()
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            future.complete(getChangelogs(since))
        })
        return future
    }

    abstract fun save(changelog: Changelog)

}