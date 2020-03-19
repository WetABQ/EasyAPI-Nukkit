package top.wetabq.easyapi.config.encoder.advance

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import top.wetabq.easyapi.config.encoder.CodecEasyConfig

abstract class AdvanceCodecEasyConfig<T>(
    configName:String,
    plugin: Plugin,
    private val defaultValue: T
) : CodecEasyConfig<T>(configName, plugin),
    AdvanceConfigCodec<T> {

    @Suppress("UNCHECKED_CAST")
    override fun initFromConfigSecion(configSection: ConfigSection) {
        //simpleConfig = linkedMapOf()
        (configSection as LinkedHashMap<String, Any>).forEach { (s, any) ->
            if (any is LinkedHashMap<*, *>) {
                simpleConfig[s] = decode(any as LinkedHashMap<String, *>)
            }
        }
    }

    override fun saveToConfigSection(configSection: ConfigSection) {
        configSection.clear()
        val encodeMap = LinkedHashMap<String, LinkedHashMap<String, *>>()
        simpleConfig.forEach { (key, obj) ->
            encodeMap[key] = encode(obj)
        }
        configSection.putAll(encodeMap)
    }

    fun safeGetData(key: String): T {
        if (!simpleConfig.containsKey(key)) simpleConfig[key] = defaultValue
        return simpleConfig[key]?:defaultValue
    }

}