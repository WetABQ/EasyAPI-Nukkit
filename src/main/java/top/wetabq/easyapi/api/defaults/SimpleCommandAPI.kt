/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api.defaults

import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.command.EasySubCommand
import top.wetabq.easyapi.command.defaults.EasyAPICommand

/**
 * Use EasyAPIModule build-in command to add sub-command
 * Usage: /eapi
 */
class SimpleCommandAPI : CommonDynamicIntegrateAPI<EasySubCommand, SimpleCommandAPI>() {

    override fun addInterface(t: EasySubCommand): SimpleCommandAPI {
        EasyAPICommand.subCommand.add(t)
        EasyAPICommand.loadCommandBase()
        return this
    }

    override fun removeInterface(t: EasySubCommand): SimpleCommandAPI {
        EasyAPICommand.subCommand.remove(t)
        EasyAPICommand.loadCommandBase()
        return this
    }

}