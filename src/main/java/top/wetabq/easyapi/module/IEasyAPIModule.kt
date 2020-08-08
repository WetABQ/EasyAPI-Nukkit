/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.module

import top.wetabq.easyapi.api.DynamicIntegrateAPI

interface IEasyAPIModule {

    fun getModuleInfo(): ModuleInfo

    fun getModuleStatus(): ModuleStatus

    fun getAllIntegrateAPI(): Collection<DynamicIntegrateAPI<*, *>>

    fun getIntegrateAPI(identifier: String): DynamicIntegrateAPI<*,*>?

    fun <I: DynamicIntegrateAPI<*, *>> registerAPI(identifier: String, integrateAPI: I): I

    fun register()

    fun disable()


}