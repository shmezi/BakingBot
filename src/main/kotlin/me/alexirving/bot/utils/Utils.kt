package me.alexirving.bot.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

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