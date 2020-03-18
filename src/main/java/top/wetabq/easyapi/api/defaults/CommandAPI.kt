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