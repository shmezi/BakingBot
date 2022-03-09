package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.getEventNames
import me.alexirving.bot.utils.Utils.mapToMenu
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.build.Commands


class Attend : Command {

    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        if (e.guild!!.getEventNames().isEmpty()) {
            h.editOriginal("Sorry, no event currently exist!").queue()
            return
        }
        h.editOriginal("").setActionRow(mapToMenu(e.id, e.guild!!.getEventNames())).queue()

    }

    override fun toData() = Commands.slash("attend", "Attend an event!")

}