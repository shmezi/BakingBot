package me.alexirving.bot.events

import me.alexirving.bot.commands.CommandHandler
import me.alexirving.bot.utils.psExec
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildReady(private val cm: CommandHandler) : ListenerAdapter() {
    override fun onGuildReady(e: GuildReadyEvent) {
        cm.updateGuild(e.guild)
        psExec("CREATE TABLE IF NOT EXISTS `${e.guild.id}` ( `UserId` VARCHAR(16) NOT NULL, `Settings` VARCHAR(16), PRIMARY KEY (`UserId`));")
    }
}