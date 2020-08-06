package top.wetabq.easyapi.config.encoder.external

interface ExternalSerializerEasyConfig<T> {

    fun safeGet(): T

}