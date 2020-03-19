package top.wetabq.easyapi.config.encoder.advance

import cn.nukkit.plugin.Plugin

abstract class SimpleCodecEasyConfig<T>(
    configName:String,
    val plugin: Plugin,
    val clazzT: Class<T>,
    defaultValue: T,
    private val codec: AdvanceConfigCodec<T> = ReflectionConfigCodec(
        clazzT
    )
) : AdvanceCodecEasyConfig<T>(configName, plugin, defaultValue) {

    override fun encode(obj: T): LinkedHashMap<String, *> = codec.encode(obj)

    override fun decode(map: LinkedHashMap<String, *>): T = codec.decode(map)

    override fun decode(any: Any): T = codec.decode(any)

}