package top.wetabq.easyapi.module

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.module.default.CharFormatModule
import top.wetabq.easyapi.module.default.EasyModule
import java.util.concurrent.ConcurrentHashMap

object EasyAPIModuleManager {

    private val modules = ConcurrentHashMap<ModuleInfo, EasyAPIModule>()

    fun register(module: EasyAPIModule) {
        module.register(ModuleRegistry())
        modules[module.getModuleInfo()] = module
        val info = module.getModuleInfo()
        EasyAPI.logger.info("Module ${info.name}_${info.moduleVersion} from ${info.moduleOwner.name} by ${info.author} loaded")
    }

    fun registerDefault() {
        register(EasyModule)
        register(CharFormatModule)
    }

    fun disable(module: EasyAPIModule) {
        module.disable()
        module.getModuleRegistry().getAllIntegrateAPI().forEach { api ->
            if (!api.javaClass.isAnnotationPresent(DisableNotRemoveAll::class.java)) api.removeAll()
        }
    }

    fun disableAll() {
        modules.forEach { (info, module) ->
            disable(module)
            EasyAPI.logger.warning("Module ${info.name}_${info.moduleVersion} from ${info.moduleOwner.name} by ${info.author} disabled")
        }
    }

    fun getModule(moduleInfo: ModuleInfo) : EasyAPIModule? = modules[moduleInfo]



}