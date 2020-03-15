package top.wetabq.easyapi.module

import top.wetabq.easyapi.module.exception.ModuleNotRegisterException
import top.wetabq.easyapi.module.exception.ModuleStatusException

abstract class SimpleEasyAPIModule: EasyAPIModule {

    protected var status = ModuleStatus.UNLOAD
    private lateinit var registry: ModuleRegistry

    override fun getModuleRegistry(): ModuleRegistry {
        return when (this.status) {
            ModuleStatus.ENABLED -> this.registry
            else -> throw ModuleNotRegisterException(this, "Can not get registry when module not register")
        }
    }

    override fun getModuleStatus(): ModuleStatus = status

    override fun register(registry: ModuleRegistry) {
        when (this.status) {
            ModuleStatus.DISABLED, ModuleStatus.UNLOAD -> {
                this.registry = registry
                this.status = ModuleStatus.ENABLED
            }
            ModuleStatus.ENABLED -> throw ModuleStatusException(this, "module has registered")
        }
    }

    override fun disable() {
        this.status = ModuleStatus.DISABLED
    }


}