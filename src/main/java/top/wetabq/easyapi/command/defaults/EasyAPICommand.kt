/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.command.defaults

import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParameter
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.command.EasyCommand
import top.wetabq.easyapi.command.EasySubCommand
import top.wetabq.easyapi.config.defaults.SimpleConfig
import top.wetabq.easyapi.utils.color

object EasyAPICommand : EasyCommand("eapi", "EasyAPI Command") {

    init {
        subCommand.add(object: EasySubCommand("version") {
            override fun getParameters(): Array<CommandParameter>? {
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
                        "If you have any questions, please feel free to send us feedback to our email wetabq@gmail.com".color())
                return true
            }

        })
        subCommand.add(object: EasySubCommand("reload") {
            override fun getParameters(): Array<CommandParameter>? {
                return null
            }

            override fun getAliases(): Array<String> {
                return arrayOf("reloadConfig")
            }

            override fun getDescription(): String {
                return "Reload EasyAPI Module Config from Disk"
            }

            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                SimpleConfig.init()
                sender.sendMessage("${EasyAPI.TITLE}&aReload successfully!".color())
                return true
            }
        })
        loadCommandBase()
    }

}