/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.module

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.module.defaults.*
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
        register(PlaceholderManager)
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