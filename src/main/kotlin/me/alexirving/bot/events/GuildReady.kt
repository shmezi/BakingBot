package me.alexirving.bot.events

import me.alexirving.bot.commands.CommandHandler
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildReady(private val cm: CommandHandler) : ListenerAdapter() {
    override fun onGuildReady(e: GuildReadyEvent) {
        cm.updateGuild(e.guild)
        val id = e.guild.id
        psExec("CREATE TABLE IF NOT EXISTS `S$id` ( `EventId` BIGINT(32) NOT NULL, `End` BIGINT(32), `Name` VARCHAR(32), PRIMARY KEY (`EventId`));")
        psExec("CREATE TABLE IF NOT EXISTS `D$id` ( `UserId` VARCHAR(16) NOT NULL, PRIMARY KEY (`UserId`));")
        psExec("CREATE TABLE IF NOT EXISTS `H$id` (`UserId` VARCHAR(32) NOT NULL, `Action` VARCHAR(32), `Reason` VARCHAR(64));")
        psExec("CREATE TABLE IF NOT EXISTS `M$id` (`UserId` VARCHAR(32) NOT NULL, `Until` INT(64), PRIMARY KEY (`UserId`));")

    }
}