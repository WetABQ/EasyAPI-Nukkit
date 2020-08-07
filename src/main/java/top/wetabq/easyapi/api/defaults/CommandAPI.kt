/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api.defaults

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.command.EasyCommand

class CommandAPI: CommonDynamicIntegrateAPI<EasyCommand, CommandAPI>() {

    override fun addInterface(t: EasyCommand): CommandAPI {
        EasyAPI.server.commandMap.register( "", t)
        return this
    }

    override fun removeInterface(t: EasyCommand): CommandAPI {
        // NOT SUPPORT ACTUALLY REMOVE
        return this
    }


}