package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.command.EasySubCommand
import top.wetabq.easyapi.command.default.EasyAPICommand

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