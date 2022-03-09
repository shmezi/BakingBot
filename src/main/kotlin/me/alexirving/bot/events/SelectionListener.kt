package me.alexirving.bot.events

import me.alexirving.bot.utils.Utils.generateQr
import me.alexirving.bot.utils.Utils.psExec
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SelectionListener : ListenerAdapter() {
    override fun onSelectMenuInteraction(e: SelectMenuInteractionEvent) {
        psExec("INSERT INTO `D${e.guild?.id}` (`UserId`, `${e.interaction.values[0]}`) VALUES ('${e.user.id}', 'ATTENDING') ON CONFLICT(`UserId`) DO UPDATE SET `${e.interaction.values[0]}` = 'ATTENDING';")

        e.editMessage("You are now attending the event :).\nOnce you are done please add the qr code in a photo and run /submit")
            .retainFiles()
            .addFile(
                generateQr("${e.guild?.id}_${e.selectedOptions[0].value}_${e.user.id}"),
                "Verify-${e.user.id}-BakingEvent.png"
            ).queue()
    }
}