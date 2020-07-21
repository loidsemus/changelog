package me.loidsemus.changelog.config.lang

import de.exlll.configlib.configs.yaml.YamlConfiguration
import java.io.File

class LanguageConfig(dir: File) : YamlConfiguration(
    File(dir, "messages.yml").toPath(),
    YamlProperties.builder().addDefaultConverter(Message::class.java, MessageConverter()).build()
) {
    
    var example = Message("lol, {placeholder}")

}