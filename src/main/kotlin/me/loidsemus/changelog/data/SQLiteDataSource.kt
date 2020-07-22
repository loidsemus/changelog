package me.loidsemus.changelog.data

import co.aikar.idb.DB
import co.aikar.idb.Database
import co.aikar.idb.DatabaseOptions
import co.aikar.idb.PooledDatabaseOptions
import java.io.File
import java.util.*

class SQLiteDataSource(dir: File) : DataSource() {

    private val database: Database

    init {
        val dbOptions = DatabaseOptions.builder()
            .sqlite(File(dir, "data.db").path)
            .build()
        database = PooledDatabaseOptions.builder().options(dbOptions).createHikariDatabase()
        DB.setGlobalDatabase(database)

        database.executeUpdate(
            "CREATE TABLE IF NOT EXISTS changelogs (" +
                    "id integer PRIMARY KEY AUTOINCREMENT, " +
                    "title text, " +
                    "description text, " +
                    "date date, " +
                    "author varchar(36));"
        )
        database.executeUpdate(
            "CREATE TABLE IF NOT EXISTS features (" +
                    "id integer PRIMARY KEY AUTOINCREMENT, " +
                    "description text, " +
                    "changelog_id int, " +
                    "FOREIGN KEY (changelog_id) REFERENCES changelogs(id));"
        )
    }

    override fun getChangelogs(since: Date): List<Changelog> {
        val changelogs = database.getResults(
            "SELECT c.id as changelogId, c.title, c.description as changelogDescription, c.date, c.author, " +
                    "f.description as featureDescription, f.id as featureId " +
                    "FROM changelogs c " +
                    "LEFT JOIN features f " +
                    "ON c.id = f.changelog_id " +
                    "WHERE c.date > ? " +
                    "ORDER BY date(c.date) DESC;",
            since
        )
        val results = mutableListOf<Changelog>()
        var changelog: Changelog? = null
        for (it in changelogs) {
            // First iteration for a new changelog, add the previous to the list since it's done
            if (changelog == null || it.getInt("changelogId") != changelog.id) {
                changelog?.let {
                    results.add(it)
                }
                changelog = Changelog(
                    it.getInt("changelogId"),
                    it.getString("title"),
                    it.getString("changelogDescription"),
                    Date(it.getLong("date")),
                    UUID.fromString(it.getString("author"))
                )
                if (!it.getString("featureDescription").isNullOrEmpty()) {
                    changelog.addFeature(
                        Changelog.Feature(
                            it.getInt("featureId"),
                            it.getString("featureDescription")
                        )
                    )
                }
            } else {
                if (it.getInt("changelogId") == changelog.id) {
                    changelog.addFeature(Changelog.Feature(it.getInt("featureId"), it.getString("featureDescription")))
                }
            }

        }
        // The last changelog in the DB result will not be added in the loop
        if (changelog != null && !results.contains(changelog)) {
            results.add(changelog)
        }

        return results
    }

    override fun save(changelog: Changelog) {
        // Newly created changelog, not pulled from DB
        if (changelog.id == null) {
            val id = database.executeInsert(
                "insert into changelogs (title, description, date, author) values (?, ?, ?, ?);",
                changelog.title, changelog.description, changelog.date, changelog.author.toString()
            ).toInt()
            changelog.features.forEach {
                database.executeUpdate(
                    "insert into features (description, changelog_id) values (?, ?);",
                    it.description, id
                )
            }
            return
        }
        database.executeUpdate(
            "update changelogs set title = ?, description = ? WHERE id = ?",
            changelog.title, changelog.description, changelog.id
        )
        changelog.features.forEach {
            if (it.id == null) {
                database.executeUpdate(
                    "insert into features (description, changelog_id) values (?, ?);",
                    it.description, changelog.id
                )
            } else {
                database.executeUpdate(
                    "update features set description = ? where id = ?",
                    it.description, it.id
                )
            }
        }
    }
}