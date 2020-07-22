package me.loidsemus.changelog

import co.aikar.idb.DB
import me.loidsemus.changelog.commands.RootCommand
import me.loidsemus.changelog.commands.subcommands.NewCommand
import me.loidsemus.changelog.config.MainConfig
import me.loidsemus.changelog.config.lang.LanguageConfig
import me.loidsemus.changelog.data.DataSource
import me.loidsemus.changelog.data.SQLiteDataSource
import org.bukkit.plugin.java.JavaPlugin

class ChangelogPlugin : JavaPlugin() {

    val mainConfig = MainConfig(dataFolder)
    val messages = LanguageConfig(dataFolder)

    lateinit var dataSource: DataSource

    override fun onEnable() {
        createFiles()
        mainConfig.loadAndSave()
        messages.loadAndSave()

        dataSource = SQLiteDataSource(dataFolder)

        val rootCommand = RootCommand(this, "changelog")
        rootCommand.addSubcommand(NewCommand())
        getCommand("changelog")?.setExecutor(rootCommand)
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