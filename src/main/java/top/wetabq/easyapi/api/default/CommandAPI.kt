package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.command.EasyCommand
import top.wetabq.easyapi.module.EasyAPIModule

class CommandAPI<M: EasyAPIModule>(private val module: M): DynamicIntegrateAPI<EasyCommand, M> {

    private val commandList = arrayListOf<EasyCommand>()

    override fun add(t: EasyCommand): M {
        commandList.add(t)
        Server.getInstance().commandMap.register( "", t)
        return module
    }

    override fun remove(t: EasyCommand): M {
        // NOT SUPPORT ACTUALLY REMOVE
        commandList.remove(t)
        return module
    }

    override fun getAll(): Collection<EasyCommand> {
        return commandList
    }

    override fun removeAll() {
        commandList.forEach { t -> remove(t) }
    }


}