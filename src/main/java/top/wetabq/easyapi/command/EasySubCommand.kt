package top.wetabq.easyapi.command

import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParameter

abstract class EasySubCommand(val subCommandName: String) {

    abstract fun getParameters(): Array<CommandParameter>?

    abstract fun getAliases(): Array<String>?

    abstract fun getDescription() : String

    abstract fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean

    fun safeExecute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        var optionalSize = 0
        this.getParameters()?.forEach { if(it.optional) optionalSize++ }
        if (args.size - 1 <= (getParameters()?:arrayOf()).size && args.size - 1 >= (getParameters()?:arrayOf()).size - optionalSize) return execute(sender, label, args)
        return false
    }

}