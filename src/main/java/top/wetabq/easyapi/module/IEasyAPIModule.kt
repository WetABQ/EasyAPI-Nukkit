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