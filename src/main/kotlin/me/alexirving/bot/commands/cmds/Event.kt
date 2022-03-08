package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.psExec
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.sql.Timestamp
import java.time.Instant
import java.util.regex.Pattern

class Event : Command {
    val durValid = Pattern.compile("^(\\d+)(y|mo|w|d|h|mi|s)?\$")
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.ADMINISTRATOR) == true) {

            val v =
                durValid.matcher((e.getOption("duration")?.name ?: "odd?").lowercase()).apply { if (!matches()) return }
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
            val duration = multiplier * v.group(1).toInt()
            psExec("ALTER TABLE `${e.guild?.id}` ADD `${Timestamp.from(Instant.now())}` INT;")
            psExec("INSERT INTO EventSettings (``) ")
        } else
            h.editOriginal("You do not have permission to use this command!")
    }

    override fun toData(): CommandData {
        return Commands.slash("event", "Create an event!")
            .addOption(OptionType.STRING, "name", "Name")
            .addOption(OptionType.STRING, "duration", "Duration, example 5d or 02/02/2022")
    }
}