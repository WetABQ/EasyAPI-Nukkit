package top.wetabq.easyapi.command.default

import cn.nukkit.command.CommandSender
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.command.CommandArgument
import top.wetabq.easyapi.command.EasyCommand
import top.wetabq.easyapi.command.EasySubCommand

object EasyAPICommand : EasyCommand("eapi", "EasyAPI Command") {

    init {
        subCommand.add(object: EasySubCommand("version") {
            override fun getArguments(): Array<CommandArgument>? {
                return null
            }

            override fun getAliases(): Array<String> {
                return arrayOf("v","version")
            }

            override fun getDescription(): String {
                return "View EasyAPI version"
            }

            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                sender.sendMessage("${EasyAPI.TITLE}EasyAPI - ${EasyAPI.VERSION} - ${EasyAPI.GIT_VERSION} by WetABQ\n" +
                        "If you have any questions, please feel free to send us feedback to our email wetabq@gmail.com")
                return true
            }

        })
    }

}