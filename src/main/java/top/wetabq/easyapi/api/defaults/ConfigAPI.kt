package top.wetabq.easyapi.api.defaults

import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.config.IEasyConfig

class ConfigAPI: CommonDynamicIntegrateAPI<IEasyConfig, ConfigAPI>() {


    override fun addInterface(t: IEasyConfig): ConfigAPI {
        return this
    }

    override fun removeInterface(t: IEasyConfig): ConfigAPI {
        // t.save() - ATTENTION
        return this
    }

}