package me.alexirving.bot.commands.mod

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

class Ban : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.BAN_MEMBERS) == true) {
            val r = e.getOption("reason")?.asString ?: "Not provided!"
            psExec("INSERT INTO `H${e.guild?.id}` (`${e.user.id}`, `BAN`, `$r`);")
            h.editOriginal("User ${e.getOption("toban")?.asUser?.asMention} has been banned!\nFor reason: `$r`")
                .queue()
            e.guild?.ban(e.getOption("toban")!!.asUser, e.getOption("days")?.asInt ?: 0, e.getOption("days")?.asString)
                ?.queue()
        } else
            h.editOriginal("No permission!").queue()
    }

    override fun toData() = Commands.slash("ban", "Ban a user!")
        .addOption(OptionType.USER, "toban", "User to ban", true)
        .addOption(OptionType.STRING, "reason", "Reason to ban user!")
        .addOption(OptionType.INTEGER, "days", "Days to delete!")
}