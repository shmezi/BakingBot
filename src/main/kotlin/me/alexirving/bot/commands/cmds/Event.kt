package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.time.Instant
import java.util.regex.Pattern

class Event : Command {
    val durValid = Pattern.compile("^(\\d+)(y|mo|w|d|h|mi|s)?\$")
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.ADMINISTRATOR) == true) {
            val eventId = Instant.now().toEpochMilli()
            val guildId = e.guild?.id
            val eventName = e.getOption("name")!!.asString
            val v =
                durValid.matcher((e.getOption("duration")!!.asString).lowercase())
            if (!v.matches())
                h.editOriginal("Please format your duration properly!").queue()

            val multiplier = if (v.groupCount() == 2) {
                when (v.group(2)) {
                    "y" -> 31557600L
                    "mo" -> 2629800L
                    "w" -> 604800L
                    "d" -> 86400L
                    "h" -> 3600L
                    "mi" -> 60
                    "s" -> 1L
                    else -> 1L
                }
            } else
                1L
            val duration = (multiplier * v.group(1).toInt() + eventId)
            psExec("ALTER TABLE `D$guildId` ADD `$eventId` INT;")
            psExec("INSERT INTO `S$guildId` VALUES ('$eventId', '$duration', '$eventName');")
            h.editOriginal("Event created named \"$eventName\" that has a duration of $duration in ms")
                .queue()
        } else
            h.editOriginal("You do not have permission to use this command!")
    }

    override fun toData() = Commands.slash("event", "Create an event!")
        .addOption(OptionType.STRING, "name", "Name", true)
        .addOption(OptionType.STRING, "duration", "Duration, example 5d or 02/02/2022", true)

}