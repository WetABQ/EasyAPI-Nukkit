package top.wetabq.easyapi.module

interface EasyAPIModule {

    fun getModuleInfo(): ModuleInfo

    fun getModuleRegistry(): ModuleRegistry

    fun getModuleStatus(): ModuleStatus

    fun register(registry: ModuleRegistry)

    fun disable()


}