package top.wetabq.easyapi.module

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.module.defaults.AsyncListenerModule
import top.wetabq.easyapi.module.defaults.ChatNameTagFormatModule
import top.wetabq.easyapi.module.defaults.EasyBaseModule
import top.wetabq.easyapi.module.defaults.ScreenShowModule
import java.util.concurrent.ConcurrentHashMap

object EasyAPIModuleManager {

    private val modules = ConcurrentHashMap<ModuleInfo, IEasyAPIModule>()

    fun register(module: IEasyAPIModule) {
        module.register()
        modules[module.getModuleInfo()] = module
        val info = module.getModuleInfo()
        EasyAPI.INSTANCE.logger.info("Module ${info.name}_${info.moduleVersion} from ${info.moduleOwner.name} by ${info.author} loaded")
    }

    fun registerDefault() {
        register(EasyBaseModule)
        register(ChatNameTagFormatModule)
        register(AsyncListenerModule)
        register(ScreenShowModule)
    }

    fun disable(module: IEasyAPIModule) {
        module.disable()
        modules.remove(module.getModuleInfo())
        module.getAllIntegrateAPI().forEach { api ->
            if (!api.javaClass.isAnnotationPresent(DisableNotRemoveAll::class.java)) api.removeAll()
        }
        val info = module.getModuleInfo()
        EasyAPI.INSTANCE.logger.warning("Module ${info.name}_${info.moduleVersion} from ${info.moduleOwner.name} by ${info.author} disabled")
    }

    fun disableAll() {
        modules.values.forEach { disable(it) }
    }

    fun getModule(moduleInfo: ModuleInfo) : IEasyAPIModule? = modules[moduleInfo]

    fun getAllModule(): Map<ModuleInfo, IEasyAPIModule> = modules



}