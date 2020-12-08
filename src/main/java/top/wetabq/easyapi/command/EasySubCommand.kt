/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

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