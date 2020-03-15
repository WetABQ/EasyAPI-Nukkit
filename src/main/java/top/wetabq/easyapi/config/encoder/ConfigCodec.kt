package top.wetabq.easyapi.config.encoder

interface ConfigCodec<T> {

    fun encode(obj: T): Any

    fun decode(any: Any): T

}