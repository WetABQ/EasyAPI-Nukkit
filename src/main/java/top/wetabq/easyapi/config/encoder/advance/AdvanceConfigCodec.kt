package top.wetabq.easyapi.config.encoder.advance

import top.wetabq.easyapi.config.encoder.ConfigCodec

interface AdvanceConfigCodec<T>: ConfigCodec<T> {

    override fun encode(obj: T): LinkedHashMap<String, *>

    fun decode(map: LinkedHashMap<String, *>): T

}