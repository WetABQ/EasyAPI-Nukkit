package top.wetabq.easyapi.module

import top.wetabq.easyapi.api.DynamicIntegrateAPI
import java.util.concurrent.ConcurrentHashMap

abstract class EasyAPIModule: IEasyAPIModule {

    private val integrateAPIs = ConcurrentHashMap<String, DynamicIntegrateAPI<*, *>>()

    override fun <I: DynamicIntegrateAPI<*, *>> registerAPI(identifier: String, integrateAPI: I): I {
        integrateAPIs[identifier] = integrateAPI
        return integrateAPI
    }

    override fun getIntegrateAPI(identifier: String): DynamicIntegrateAPI<*, *>? = integrateAPIs[identifier]

    override fun getAllIntegrateAPI(): Collection<DynamicIntegrateAPI<*, *>> = integrateAPIs.values

}