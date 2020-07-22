package me.loidsemus.changelog.config.lang

import de.exlll.configlib.annotation.Comment
import de.exlll.configlib.configs.yaml.YamlConfiguration
import de.exlll.configlib.format.FieldNameFormatters
import java.io.File

@Comment(
    "Most if not all messages in the plugin are configurable here.",
    "To use color codes, use an ampersand (&) followed by the color code, for example &6 or &e",
    "Like usual in YAML, to use apostrophes you'll have to escape them by writing them two times.",
    "The placeholders available for a certain value are listed above it, those without listed placeholders don't have any."
)
class LanguageConfig(dir: File) : YamlConfiguration(
    File(dir, "messages.yml").toPath(),
    YamlProperties.builder().addDefaultConverter(Message::class.java, MessageConverter())
        .setFormatter(FieldNameFormatters.LOWER_UNDERSCORE).build()
) {

    @Comment("Available placeholders: {command}")
    var helpHeader = Message("&eShowing all subcommands for &r&e/{command}")
    var helpHeaderConsole = Message("&eShowing all console subcommands for &r&e/{command}")

    @Comment("Available placeholders: {fullCommand}, {description}")
    var helpEntry = Message("&c&l{fullCommand} &r- {description}")
    var consolesCanNotRun = Message("&c&lConsoles can not run this command")

}