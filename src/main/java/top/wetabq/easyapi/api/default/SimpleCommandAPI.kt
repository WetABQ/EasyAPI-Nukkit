package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.command.EasySubCommand
import top.wetabq.easyapi.command.default.EasyAPICommand

/**
 * Use EasyAPIModule build-in command to add sub-command
 * Usage: /eapi
 */
class SimpleCommandAPI : DynamicIntegrateAPI<EasySubCommand, SimpleCommandAPI> {

    private val commandList = arrayListOf<EasySubCommand>()

    override fun add(t: EasySubCommand): SimpleCommandAPI {
        EasyAPICommand.subCommand.add(t)
        EasyAPICommand.loadCommandBase()
        return this
    }

    override fun remove(t: EasySubCommand): SimpleCommandAPI {
        EasyAPICommand.subCommand.remove(t)
        EasyAPICommand.loadCommandBase()
        return this
    }

    override fun getAll(): Collection<EasySubCommand> = commandList

    override fun removeAll() {
        commandList.forEach { t -> remove(t) }
    }

}