package me.alexirving.bot

import me.alexirving.bot.commands.CommandHandler
import me.alexirving.bot.commands.cmds.Analyze
import me.alexirving.bot.commands.cmds.Attend
import me.alexirving.bot.commands.cmds.Event
import me.alexirving.bot.commands.cmds.Submit
import me.alexirving.bot.events.GuildReady
import me.alexirving.bot.events.SelectionListener
import me.alexirving.bot.utils.Utils.copyOver
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
    copyOver("settings.properties")

    /**
     * General code
     */
    val settings = Properties()
    settings.load(FileInputStream("settings.properties"))
    jda = JDABuilder.createDefault(settings.getProperty("TOKEN")).build()


    ch.register("attend", Attend())
    ch.register("event", Event())
    ch.register("submit", Submit())
    ch.register("analyze", Analyze())
    jda.addEventListener(ch, GuildReady(ch), SelectionListener())

}


/**
 * Template made by Alex Irving https://github.com/shmezi
 */