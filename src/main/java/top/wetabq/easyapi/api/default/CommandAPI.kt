package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.command.EasyCommand

class CommandAPI: DynamicIntegrateAPI<EasyCommand, CommandAPI> {

    private val commandList = arrayListOf<EasyCommand>()

    override fun add(t: EasyCommand): CommandAPI {
        commandList.add(t)
        Server.getInstance().commandMap.register( "", t)
        return this
    }

    override fun remove(t: EasyCommand): CommandAPI {
        // NOT SUPPORT ACTUALLY REMOVE
        commandList.remove(t)
        return this
    }

    override fun getAll(): Collection<EasyCommand> {
        return commandList
    }

    override fun removeAll() {
        commandList.forEach { t -> remove(t) }
    }


}