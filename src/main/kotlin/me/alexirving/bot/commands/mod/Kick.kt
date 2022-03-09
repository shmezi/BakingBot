package me.alexirving.bot.commands.mod

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

class Kick : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.KICK_MEMBERS) == true) {
            val r = e.getOption("reason")?.asString ?: "Not provided!"
            psExec("INSERT INTO `H${e.guild?.id}` (`${e.user.id}`, `KICK`, `$r`);")
            h.editOriginal("User ${e.getOption("tokick")?.asUser?.asMention} has been kicked!\nFor reason: `$r`")
                .queue()
            e.guild?.kick(e.getOption("tokick")!!.asMember!!, e.getOption("days")?.asString)
                ?.queue()
        } else
            h.editOriginal("No permission!").queue()
    }

    override fun toData() = Commands.slash("kick", "Kick a user!")
        .addOption(OptionType.USER, "tokick", "User to kick", true)
        .addOption(OptionType.STRING, "reason", "Reason to kick user!")
}