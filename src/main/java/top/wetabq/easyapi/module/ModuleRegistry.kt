package top.wetabq.easyapi.module

import top.wetabq.easyapi.api.DynamicIntegrateAPI

class ModuleRegistry {

    private val integrateAPIList = ArrayList<DynamicIntegrateAPI<*>>()

    fun <I: DynamicIntegrateAPI<*>> registerAPI(integrateAPI: I): I {
        integrateAPIList.add(integrateAPI)
        return integrateAPI
    }

    fun getAllIntegrateAPI(): ArrayList<DynamicIntegrateAPI<*>> = integrateAPIList



}