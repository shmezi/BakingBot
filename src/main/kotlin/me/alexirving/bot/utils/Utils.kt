package me.alexirving.bot.utils

import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeWriter
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import javax.imageio.ImageIO


object Utils {
    val fileValid = Regex(".+\\..+")
    val hints = mutableMapOf<EncodeHintType, Any>().apply {
        this[EncodeHintType.MARGIN] = 1
        this[EncodeHintType.CHARACTER_SET] = "UTF-8"
    }
    val qr = QRCodeWriter()

    /**
     * Allows you to copy over files / create directories
     * @param names The names of the files that are copied
     */
    fun copyOver(vararg names: String) {
        for (name in names) {
            if (name.matches(fileValid)) {
                if (!File(name).exists())
                    Files.copy(
                        Thread.currentThread().contextClassLoader.getResourceAsStream(name), Path.of(name),
                        StandardCopyOption.REPLACE_EXISTING
                    )

            } else
                File(name).mkdirs()
        }
    }

    fun getDb(): Connection = DriverManager.getConnection("jdbc:sqlite:database.db")

    fun psExec(statement: String) = getDb().prepareStatement(statement).execute()
    fun psQuery(statement: String): ResultSet = getDb().prepareStatement(statement).executeQuery()

    fun Guild.getEventNames(): MutableMap<Long, String> {
        val events = mutableMapOf<Long, String>()
        val rs = psQuery("SELECT * FROM S${this.id};")
        while (rs.next())
            events[rs.getLong("EventId")] = rs.getString("Name")
        return events
    }

    fun mapToMenu(id: String, map: MutableMap<Long, String>): SelectMenu {
        val m = SelectMenu.create(id)
        m.placeholder = "Select an event to get started!"
        for (x in map)
            m.addOption(x.value, x.key.toString())
        return m.build()
    }


    fun generateQr(data: String, w: Int, h: Int): ByteArray {
        val bos = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(qr.encode(data, BarcodeFormat.QR_CODE, w, h, hints), "PNG", bos)
        return bos.toByteArray()
    }

    fun generateQr(data: String): ByteArray = generateQr(data, 250, 250)

    fun decodeQRCode(qrImg: InputStream?): String? {
        (qrImg ?: return null) .use {
            val result =
                MultiFormatReader().decode(BinaryBitmap(HybridBinarizer(BufferedImageLuminanceSource(ImageIO.read(qrImg)))))
            return result.text
        }
    }


}