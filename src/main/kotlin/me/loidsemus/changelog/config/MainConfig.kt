package me.loidsemus.changelog.config

import de.exlll.configlib.annotation.Comment
import de.exlll.configlib.configs.yaml.YamlConfiguration
import java.io.File

class MainConfig(dir: File) : YamlConfiguration(File(dir, "config.yml").toPath(), YamlProperties.DEFAULT) {

    @Comment("Comment")
    var configValue = "Config"

}