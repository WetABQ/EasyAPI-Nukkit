package top.wetabq.easyapi.api.defaults

import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.config.EasyConfig

class ConfigAPI: CommonDynamicIntegrateAPI<EasyConfig, ConfigAPI>() {


    override fun addInterface(t: EasyConfig): ConfigAPI {
        return this
    }

    override fun removeInterface(t: EasyConfig): ConfigAPI {
        // t.save() - ATTENTION
        return this
    }

}