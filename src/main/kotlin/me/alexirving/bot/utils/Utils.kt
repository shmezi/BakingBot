package me.alexirving.bot.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

val fileValid = Regex(".+\\..+")


/**
 * Allows you to copy over files / create directories
 * @param names The names of the files that are copied
 */
fun copyOver(vararg names: String) {
    for (name in names) {
        if (name.matches(fileValid)) {
            if (!File(name).exists())
                Files.copy(
                    Thread.currentThread().contextClassLoader.getResourceAsStream(name), Path.of(name),
                    StandardCopyOption.REPLACE_EXISTING
                )

        } else
            File(name).mkdirs()
    }
}

fun getDb(): Connection = DriverManager.getConnection("jdbc:sqlite:database.db")

fun psExec(statement: String) = getDb().prepareStatement(statement).execute()
fun psQuery(statement: String): ResultSet = getDb().prepareStatement(statement).executeQuery()