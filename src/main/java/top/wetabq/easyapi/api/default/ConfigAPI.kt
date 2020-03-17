package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.config.EasyConfig

class ConfigAPI: DynamicIntegrateAPI<EasyConfig, ConfigAPI> {

    private val configList = arrayListOf<EasyConfig>()

    override fun add(t: EasyConfig): ConfigAPI {
        configList.add(t)
        return this
    }

    override fun remove(t: EasyConfig): ConfigAPI {
        t.save()
        return this
    }

    override fun getAll(): Collection<EasyConfig> = configList

    override fun removeAll() {
        configList.forEach { t -> remove(t) }
    }

}