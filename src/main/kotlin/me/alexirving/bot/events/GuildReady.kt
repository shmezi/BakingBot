package me.alexirving.bot.events

import me.alexirving.bot.commands.CommandHandler
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildReady(private val cm: CommandHandler) : ListenerAdapter() {
    override fun onGuildReady(e: GuildReadyEvent) {
        cm.updateGuild(e.guild)
        psExec("CREATE TABLE IF NOT EXISTS `S${e.guild.id}` ( `EventId` BIGINT(32) NOT NULL, `End` BIGINT(32), `Name` VARCHAR(32), PRIMARY KEY (`EventId`));")
        psExec("CREATE TABLE IF NOT EXISTS `D${e.guild.id}` ( `UserId` VARCHAR(16) NOT NULL, PRIMARY KEY (`UserId`));")

    }
}