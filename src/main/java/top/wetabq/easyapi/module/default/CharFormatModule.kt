package top.wetabq.easyapi.module.default

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleRegistry
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule

object CharFormatModule : SimpleEasyAPIModule() {

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI,
        "ChatFormatModule",
        "WetABQ",
        ModuleVersion(1, 0, 0)
    )

    override fun register(registry: ModuleRegistry) {
        super.register(registry)
    }

    override fun disable() {
        super.disable()
    }


}