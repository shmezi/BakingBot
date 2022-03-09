package me.alexirving.bot.commands.mod

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

class UnMute : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.MESSAGE_MANAGE) == true) {
            val muteRole = e.guild?.getRolesByName("Muted", true)?.get(0) ?: return
            psExec("DELETE FROM `M${e.guild?.id}` WHERE 'UserId' = '${e.user.id}');")
            h.editOriginal("User ${e.getOption("tounmute")?.asUser?.asMention} has been unmuted")
                .queue()
            e.guild?.removeRoleFromMember(e.getOption("tounmute")?.asMember!!, muteRole)?.queue()

        } else
            h.editOriginal("No permission!").queue()
    }

    override fun toData() = Commands.slash("unmute", "Unmute a user!")
        .addOption(OptionType.USER, "tounmute", "User to unmute", true)
}