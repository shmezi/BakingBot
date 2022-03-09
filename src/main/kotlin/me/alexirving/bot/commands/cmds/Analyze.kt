package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils.decodeQRCode
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.awt.Color

class Analyze : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        val eb = EmbedBuilder().setTitle("Analyzer")
        val dc = decodeQRCode(e.getOption("image")?.asAttachment?.retrieveInputStream()?.get())

        if (dc != null) {
            val b = dc.split("_")
            eb.setImage(e.getOption("image")?.asAttachment?.url)
            eb.setColor(Color.getHSBColor(0f, 219f, 211f))
            eb.appendDescription(">>> **GuildId:** ${b[0]}\n>>> **EventId:** ${b[1]}\n>>> **UserId:** ${b[2]}")
        } else {
            eb.setColor(Color.RED)
            eb.appendDescription("Sorry, this qrcode is invalid!")
        }
        e.hook.editOriginalEmbeds(eb.build()).queue()
    }

    override fun toData() = Commands.slash("analyze", "Analyze a submition photo")
        .addOption(OptionType.ATTACHMENT, "image", "Image to inspect", true)


}