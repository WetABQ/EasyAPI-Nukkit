package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.command.EasyCommand
import top.wetabq.easyapi.module.EasyAPIModule
import top.wetabq.easyapi.module.ModuleRegistry

class CommandAPI(private val module: EasyAPIModule): DynamicIntegrateAPI<EasyCommand> {

    private val commandList = arrayListOf<EasyCommand>()

    override fun add(t: EasyCommand): ModuleRegistry {
        commandList.add(t)
        Server.getInstance().commandMap.register( "", t)
        return module.getModuleRegistry()
    }

    override fun remove(t: EasyCommand): ModuleRegistry {
        // NOT SUPPORT ACTUALLY REMOVE
        commandList.remove(t)
        return module.getModuleRegistry()
    }

    override fun getAll(): Collection<EasyCommand> {
        return commandList
    }

    override fun removeAll() {
        commandList.forEach { t -> remove(t) }
    }


}