package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.command.EasyCommand

class CommandAPI: CommonDynamicIntegrateAPI<EasyCommand, CommandAPI>() {

    override fun addInterface(t: EasyCommand): CommandAPI {
        Server.getInstance().commandMap.register( "", t)
        return this
    }

    override fun removeInterface(t: EasyCommand): CommandAPI {
        // NOT SUPPORT ACTUALLY REMOVE
        return this
    }


}