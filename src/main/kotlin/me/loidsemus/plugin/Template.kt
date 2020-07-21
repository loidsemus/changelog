package me.loidsemus.plugin

import co.aikar.idb.DB
import co.aikar.idb.DatabaseOptions
import co.aikar.idb.PooledDatabaseOptions
import me.loidsemus.plugin.commands.MainCommand
import me.loidsemus.plugin.config.MainConfig
import me.loidsemus.plugin.config.lang.LanguageConfig
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

// TODO: Rename class
class Template : JavaPlugin() {

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
        getCommand("template")?.setExecutor(MainCommand())
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