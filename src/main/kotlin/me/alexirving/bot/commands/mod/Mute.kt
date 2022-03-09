package me.alexirving.bot.commands.mod

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.psExec
import me.alexirving.bot.utils.Utils.toTime
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.time.Instant
import java.util.*
import kotlin.concurrent.schedule

class Mute : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.BAN_MEMBERS) == true) {
            val muteRole = e.guild?.getRolesByName("Muted", true)?.get(0) ?: return
            val r = e.getOption("reason")?.asString ?: "Not provided!"
            val end = e.getOption("days")?.asString?.toTime()?.plus(Instant.now().toEpochMilli()) ?: -1

            psExec("INSERT INTO `H${e.guild?.id}` (`${e.user.id}`, `MUTE`, `$r`);")
            psExec("INSERT INTO `M${e.guild?.id}` (`${e.user.id}`, `$end`) ON CONFLICT('UserId') DO UPDATE SET 'Until' = '$end';")
            h.editOriginal("User ${e.getOption("tomute")?.asUser?.asMention} has been muted!\nFor reason: `$r`")
                .queue()
            e.guild?.addRoleToMember(e.getOption("tomute")?.asMember!!, muteRole)?.queue {
                Timer().schedule(e.getOption("days")?.asString?.toTime() ?: 0L) {
                    e.guild?.removeRoleFromMember(e.getOption("tomute")?.asMember!!, muteRole)?.queue()
                }
            }
        } else
            h.editOriginal("No permission!").queue()
    }

    override fun toData() = Commands.slash("mute", "Mute a user!")
        .addOption(OptionType.USER, "tomute", "User to mute", true)
        .addOption(OptionType.STRING, "reason", "Reason to mute user!")
        .addOption(OptionType.STRING, "days", "Days to mute for | blank for perm!")
}