package top.wetabq.easyapi.config.encoder.external

import top.wetabq.easyapi.config.IEasyConfig

interface ExternalSerializerEasyConfig<T>: IEasyConfig {

    fun safeGet(): T

}