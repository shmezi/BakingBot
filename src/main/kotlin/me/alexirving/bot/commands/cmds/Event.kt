package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.psExec
import me.alexirving.bot.utils.Utils.toTime
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.time.Instant

class Event : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.ADMINISTRATOR) == true) {
            val eventId = Instant.now().toEpochMilli()
            val guildId = e.guild?.id
            val eventName = e.getOption("name")!!.asString
            val duration = (e.getOption("duration")!!.asString).toTime()

            if (duration == null) {
                e.hook.editOriginal("Improper formatted time!").queue()
                return
            }

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