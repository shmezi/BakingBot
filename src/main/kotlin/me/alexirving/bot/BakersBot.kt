package me.alexirving.bot

import me.alexirving.bot.commands.CommandHandler
import me.alexirving.bot.commands.cmds.Attend
import me.alexirving.bot.commands.cmds.Event
import me.alexirving.bot.events.GuildReady
import me.alexirving.bot.utils.copyOver
import me.alexirving.bot.utils.psExec
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import java.io.FileInputStream
import java.util.*

lateinit var jda: JDA
val ch = CommandHandler()

fun main() {
    /**
     * Setup
     */
    copyOver("settings.properties", "sqlite.properties")

    /**
     * General code
     */
    val settings = Properties()
    settings.load(FileInputStream("settings.properties"))
    jda = JDABuilder.createDefault(settings.getProperty("TOKEN")).build()


    ch.register("attend", Attend())
    ch.register("event", Event())
    jda.addEventListener(ch, GuildReady(ch))

}


/**
 * Template made by Alex Irving https://github.com/shmezi
 */