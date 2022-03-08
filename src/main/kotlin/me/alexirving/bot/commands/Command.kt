package me.alexirving.bot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface Command {
    fun run(e: SlashCommandInteractionEvent, h: InteractionHook)
    fun toData(): CommandData
}