package top.wetabq.easyapi.module.default

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.default.CommandAPI
import top.wetabq.easyapi.command.default.EasyAPICommand
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleRegistry
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule

object EasyModule: SimpleEasyAPIModule() {

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI,
        "EasyModule",
        "WetABQ",
        ModuleVersion(1,0,0)
    )

    override fun register(registry: ModuleRegistry) {
        super.register(registry)
        registry.registerAPI(CommandAPI(this)).add(EasyAPICommand())
    }

    override fun disable() {
        super.disable()
    }

}