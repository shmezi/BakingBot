package me.alexirving.bot.commands

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class CommandHandler : ListenerAdapter() {
    private val commandMap = hashMapOf<String, Command>()
    override fun onSlashCommandInteraction(e: SlashCommandInteractionEvent) {
        e.guild ?: return
        e.deferReply().setEphemeral(true).queue()
        commandMap[e.name]?.run(e, e.hook) ?: run {
            e.hook.editOriginal("[ERROR] Command does not exist, if you are the owner of this bot please remove it from the registered commands in discord.")
                .queue()
        }

    }

    fun register(name: String, command: Command): CommandHandler {
        if (!commandMap.containsKey(name)) {
            commandMap[name] = command
            return this
        } else throw CommandAlreadyExistsException("Command '$name' is already registered!")
    }

    fun updateGuild(g: Guild) {
        val c = mutableListOf<CommandData>()
        for (cmd in commandMap.values)
            c.add(cmd.toData())

        g.updateCommands().addCommands(c).queue()
    }
}