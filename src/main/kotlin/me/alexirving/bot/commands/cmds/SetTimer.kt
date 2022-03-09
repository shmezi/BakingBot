package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.toTime
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.util.*
import kotlin.concurrent.schedule

class SetTimer : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        e.hook.editOriginal("A timer has been set for: `${e.getOption("length")?.asString}`, Make sure to open your dms!")
            .queue()
        Timer().schedule(e.getOption("length")?.asString?.toTime() ?: 0L) {
            e.user.openPrivateChannel().queue {
                if (e.getOption("label") != null) {
                    it.sendMessage("${e.member?.asMention} Your timer for `${e.getOption("label")?.asString}` is up!")
                        .queue({},{
                            e.hook.editOriginal("Couldn't dm your timer alert!").queue()
                        })
                } else
                    it.sendMessage("${e.member?.asMention} Your timer is up!")
                        .queue({},{
                            e.hook.editOriginal("Couldn't dm your timer alert!").queue()
                        })
            }

        }
    }

    override fun toData() = Commands.slash("timer", "Set a timer for a duration")
        .addOption(OptionType.STRING, "length", "Length of timer!", true)
        .addOption(OptionType.STRING, "label", "Label for timer!")
}