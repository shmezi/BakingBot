package me.alexirving.bot.commands.cmds

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import me.alexirving.bot.commands.Command
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.InteractionHook
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import java.io.ByteArrayOutputStream


class Attend : Command {
    val hints = mutableMapOf<EncodeHintType, Any>()
    val qr = QRCodeWriter()

    init {
        hints[EncodeHintType.MARGIN] = 0
    }
    override fun run(e: SlashCommandInteractionEvent, h: InteractionHook) {
        val bos = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(qr.encode(e.user.id, BarcodeFormat.QR_CODE, 100, 100, hints), "PNG", bos)
        h.editOriginal("").addFile(bos.toByteArray(), "shmezi.png").queue()

    }

    override fun toData(): CommandData {
        return Commands.slash("attend", "Attend an event!")
    }
}