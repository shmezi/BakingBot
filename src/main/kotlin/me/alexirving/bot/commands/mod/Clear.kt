package me.alexirving.bot.commands.mod

import me.alexirving.bot.commands.Command
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

class Clear : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.member?.hasPermission(Permission.MESSAGE_MANAGE) == true) {
            e.channel.history.retrievePast(e.getOption("toclear")?.asInt ?: 0).queue {
                for (m in it)
                    m.delete().queue()
            }

            h.editOriginal("Deleted `${e.getOption("toclear")?.asInt}` messages!").queue()
        } else
            h.editOriginal("No permission!").queue()
    }

    override fun toData() = Commands.slash("clear", "Clears the desired amount of messages")
        .addOption(OptionType.INTEGER, "toclear", "Amount to clear", true)
}