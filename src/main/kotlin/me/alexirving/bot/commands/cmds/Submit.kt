package me.alexirving.bot.commands.cmds

import me.alexirving.bot.commands.Command
import me.alexirving.bot.utils.Utils
import me.alexirving.bot.utils.Utils.getEventNames
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

class Submit : Command {
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        val eb = EmbedBuilder().setTitle("Submission by ${e.user.name}:")
        val dc = Utils.decodeQRCode(e.getOption("image")?.asAttachment?.retrieveInputStream()?.get())
        if (dc != null) {
            val b = dc.split("_")
            if (b[0] == e.guild?.id && (e.guild?.getEventNames()
                    ?: mutableMapOf()).containsKey(b[1].toLong()) && e.user.id == b[2]
            ) {
                eb.setImage(e.getOption("image")?.asAttachment?.url)
                e.guild?.getNewsChannelById("951156429191008307")?.sendMessageEmbeds(eb.build())?.queue{}
            }else
                h.editOriginal("Sorry, the qr code provided in the image is invalid!").queue()
        } else
            h.editOriginal("Sorry, we could not find a qr code in this image!").queue()
    }

    override fun toData() = Commands.slash("submit", "Submit to the competition")
        .addOption(OptionType.ATTACHMENT, "image", "Image of your submission", true)


}