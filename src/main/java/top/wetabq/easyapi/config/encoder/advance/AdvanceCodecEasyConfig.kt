package top.wetabq.easyapi.config.encoder.advance

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import top.wetabq.easyapi.config.encoder.CodecEasyConfig

abstract class AdvanceCodecEasyConfig<T>(
    configName:String,
    plugin: Plugin
) : CodecEasyConfig<T>(configName, plugin),
    AdvanceConfigCodec<T> {

    @Suppress("UNCHECKED_CAST")
    override fun initFromConfigSecion(configSection: ConfigSection) {
        (configSection[sectionName] as LinkedHashMap<String, Any>).forEach { (s, any) ->
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
        configSection[sectionName] = encodeMap
    }

}