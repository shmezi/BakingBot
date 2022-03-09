package me.alexirving.bot

import me.alexirving.bot.commands.CommandHandler
import me.alexirving.bot.commands.cmds.*
import me.alexirving.bot.commands.mod.*
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
        .register("event", Event())
        .register("submit", Submit())
        .register("analyze", Analyze())
        .register("timer", SetTimer())
        .register("clear", Clear())
        .register("ban", Ban())
        .register("kick", Kick())
        .register("mute", Mute())
        .register("unmute", UnMute())
    jda.addEventListener(ch, GuildReady(ch), SelectionListener())

}


/**
 * Template made by Alex Irving https://github.com/shmezi
 */