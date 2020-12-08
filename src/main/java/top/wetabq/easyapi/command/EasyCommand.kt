/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.command

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParameter
import cn.nukkit.utils.TextFormat
import java.lang.StringBuilder

abstract class EasyCommand(private val command: String, description: String = "Easy Command") : Command(command) {

    val subCommand = ArrayList<EasySubCommand>()

    init {
        this.setDescription(description)
        addHelp()
    }

    fun loadCommandBase() {
        /*this.setCommandParameters(object : HashMap<String, Array<CommandParameter>>() {
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
        })*/
        this.commandParameters.clear()
        subCommand.forEach { this.commandParameters[it.subCommandName] = arrayOf(
            CommandParameter("arg", arrayOf(it.subCommandName).plus(it.getAliases()?: arrayOf()))
        ).plus(it.getParameters()?: arrayOf()) }
        this.usage = "/${command} <subcommand> [args]"
    }

    fun addHelp() {
        this.subCommand.add(object: EasySubCommand("help") {

            override fun getParameters(): Array<CommandParameter>? = null

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

    // /eapi xxxx <argName: argType> [argName: argType]

    open fun sendHelp(sender: CommandSender) {
        sender.sendMessage("${TextFormat.GOLD}------Help------")
        subCommand.forEach { sc ->
            var str = StringBuilder()
            sc.getAliases()?.forEach {aliases -> str.append("$aliases|") }
            val args = StringBuilder()
            sc.getParameters()?.forEach {
                if (it.optional) args.append("[") else args.append("<")
                args.append(it.name)
                args.append(": ")
                args.append(it.name)
                if (it.optional) args.append("]") else args.append("> ")
            }
            if (str.toString() != "") str = StringBuilder(str.insert(0, "(").substring(0, str.length - 1) + ")")
            sender.sendMessage("${TextFormat.AQUA}/${command} ${sc.subCommandName}$str $args- ${sc.getDescription()}")
        }
    }



}