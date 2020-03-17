package top.wetabq.easyapi.module

import top.wetabq.easyapi.module.exception.ModuleStatusException

abstract class SimpleEasyAPIModule: EasyAPIModule() {

    protected var status = ModuleStatus.UNLOAD


    override fun getModuleStatus(): ModuleStatus = status

    override fun register() {
        when (this.status) {
            ModuleStatus.DISABLED, ModuleStatus.UNLOAD -> {
                this.status = ModuleStatus.ENABLED
            }
            ModuleStatus.ENABLED -> throw ModuleStatusException(this, "module has registered")
        }
        moduleRegister()
    }

    abstract fun moduleRegister()

    abstract fun moduleDisable()

    override fun disable() {
        this.status = ModuleStatus.DISABLED
        moduleDisable()
    }


}