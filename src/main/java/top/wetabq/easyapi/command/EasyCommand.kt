package top.wetabq.easyapi.command

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParameter
import cn.nukkit.utils.TextFormat
import java.lang.StringBuilder
import java.util.HashMap

abstract class EasyCommand(private val command: String, description: String = "Easy Command") : Command(command) {

    val subCommand = ArrayList<EasySubCommand>()

    init {
        this.setDescription(description)
        loadCommandBase()
        addHelp()
    }

    fun loadCommandBase() {
        this.setCommandParameters(object : HashMap<String, Array<CommandParameter>>() {
            init {
                var i = 1
                subCommand.forEach { sc ->
                    val strs = StringBuilder()
                    strs.let{ sc.getAliases()?.forEach { aliases -> it.append("$aliases/")  }}
                    if (strs.toString() != "") strs.insert(0, "(").append(")")
                    if (sc.getArguments() != null) {
                        val commandParameters = arrayOf(CommandParameter("${sc.subCommandName}$strs", false, sc.getAliases()))
                        sc.getArguments()?.forEach { argSetting -> commandParameters.plus(CommandParameter(argSetting.argName,argSetting.argType,argSetting.optional)) }
                        put("${i}arg", commandParameters)
                    } else put("${i}arg", arrayOf(CommandParameter("${sc.subCommandName}${strs}", false, sc.getAliases())))
                    i++
                }
            }
        })
        this.usage = "/${command} <subcommand> [args]"
    }

    fun addHelp() {
        this.subCommand.add(object: EasySubCommand("help") {

            override fun getArguments(): Array<CommandArgument>? = null

            override fun getAliases(): Array<String>? = null

            override fun getDescription(): String = "View help"

            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                sendHelp(sender)
                return true
            }

        })
    }

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sendHelp(sender)
            return true
        }
        subCommand.forEach { sc ->
            if (args[0] == sc.subCommandName) {
                val result = sc.safeExecute(sender, label, args)
                if (!result) sendHelp(sender)
                return result
            }
            sc.getAliases()?.forEach { aliases ->
                if (args[0] == aliases) {
                    val result = sc.safeExecute(sender, label, args)
                    if (!result) sendHelp(sender)
                    return result
                }
            }
        }
        sendHelp(sender)
        return true
    }

    open fun sendHelp(sender: CommandSender) {
        sender.sendMessage(TextFormat.GOLD.toString() + "----Help----")
        subCommand.forEach { sc ->
            val strs = StringBuilder()
            sc.getAliases()?.forEach {aliases -> strs.append(aliases) }
            if (strs.toString() != "") strs.insert(0, "(").append(")")
            sender.sendMessage("${TextFormat.AQUA}/${command} ${sc.subCommandName}$strs - ${sc.getDescription()}")
        }
    }



}