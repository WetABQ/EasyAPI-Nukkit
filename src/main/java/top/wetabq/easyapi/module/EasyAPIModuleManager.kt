package top.wetabq.easyapi.module

import top.wetabq.easyapi.module.default.CharFormatModule
import java.util.concurrent.ConcurrentHashMap

object EasyAPIModuleManager {

    private val modules = ConcurrentHashMap<ModuleInfo, EasyAPIModule>()

    fun register(module: EasyAPIModule) {
        module.register(ModuleRegistry())
        modules[module.getModuleInfo()] = module
    }

    fun registerDefault() {
        register(CharFormatModule)
    }

    fun disable(module: EasyAPIModule) {
        module.disable()
        module.getModuleRegistry().getAllIntegrateAPI().forEach { api ->
            api.removeAll()
        }
    }

    fun getModule(moduleInfo: ModuleInfo) : EasyAPIModule? = modules[moduleInfo]



}