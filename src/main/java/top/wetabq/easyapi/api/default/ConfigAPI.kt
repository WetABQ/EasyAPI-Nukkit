package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.config.EasyConfig
import top.wetabq.easyapi.module.EasyAPIModule
import top.wetabq.easyapi.module.ModuleRegistry

class ConfigAPI(private val module: EasyAPIModule): DynamicIntegrateAPI<EasyConfig> {

    private val configList = arrayListOf<EasyConfig>()

    override fun add(t: EasyConfig): ModuleRegistry {
        configList.add(t)
        return module.getModuleRegistry()
    }

    override fun remove(t: EasyConfig): ModuleRegistry {
        t.save()
        return module.getModuleRegistry()
    }

    override fun getAll(): Collection<EasyConfig> = configList

    override fun removeAll() {
        configList.forEach { t -> remove(t) }
    }

}