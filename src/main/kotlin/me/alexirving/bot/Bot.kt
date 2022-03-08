package me.alexirving.bot

import me.alexirving.bot.utils.copyOver
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import java.io.FileInputStream
import java.util.*

lateinit var jda: JDA


fun main() {
    /**
     * Setup
     */
    copyOver("settings.properties")

    /**
     * General code
     */
    val settings = Properties()
    settings.load(FileInputStream("settings.properties"))
    jda = JDABuilder.createDefault(settings.getProperty("TOKEN") ?: return).build()
    jda.addEventListener()

}


/**
 * Template made by Alex Irving https://github.com/shmezi
 */