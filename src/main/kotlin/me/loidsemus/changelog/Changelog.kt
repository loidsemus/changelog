package me.loidsemus.changelog

import co.aikar.idb.DB
import co.aikar.idb.DatabaseOptions
import co.aikar.idb.PooledDatabaseOptions
import me.loidsemus.changelog.commands.MainCommand
import me.loidsemus.changelog.config.MainConfig
import me.loidsemus.changelog.config.lang.LanguageConfig
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

// TODO: Rename class
class Changelog : JavaPlugin() {

    val mainConfig = MainConfig(dataFolder)
    val messages = LanguageConfig(dataFolder)

    override fun onEnable() {
        createFiles()
        mainConfig.loadAndSave()
        messages.loadAndSave()

        val dbOptions = DatabaseOptions.builder()
            .sqlite(File(dataFolder, "data.db").path)
            .logger(this.logger)
            .build()
        val database = PooledDatabaseOptions.builder().options(dbOptions).createHikariDatabase()
        DB.setGlobalDatabase(database)

        // TODO: Replace command name
        getCommand("changelog")?.setExecutor(MainCommand())
    }

    override fun onDisable() {
        DB.close()
    }

    private fun createFiles() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
    }

}