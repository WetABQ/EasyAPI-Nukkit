package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.command.EasySubCommand
import top.wetabq.easyapi.command.default.EasyAPICommand
import top.wetabq.easyapi.module.EasyAPIModule
import top.wetabq.easyapi.module.ModuleRegistry

/**
 * Use EasyAPIModule build-in command to add sub-command
 * Usage: /eapi
 */
class SimpleCommandAPI(private val module: EasyAPIModule) : DynamicIntegrateAPI<EasySubCommand> {

    private val commandList = arrayListOf<EasySubCommand>()

    override fun add(t: EasySubCommand): ModuleRegistry {
        EasyAPICommand.subCommand.add(t)
        EasyAPICommand.loadCommandBase()
        return module.getModuleRegistry()
    }

    override fun remove(t: EasySubCommand): ModuleRegistry {
        EasyAPICommand.subCommand.remove(t)
        EasyAPICommand.loadCommandBase()
        return module.getModuleRegistry()
    }

    override fun getAll(): Collection<EasySubCommand> = commandList

    override fun removeAll() {
        commandList.forEach { t -> remove(t) }
    }

}