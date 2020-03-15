package top.wetabq.easyapi.config.encoder.advance

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import top.wetabq.easyapi.config.encoder.CodecEasyConfig

abstract class AdvanceCodecEasyConfig<T>(
    configName:String,
    plugin: Plugin,
    private val sectionName: String = "config"
) : CodecEasyConfig<T>(configName, plugin, sectionName),
    AdvanceConfigCodec<T> {

    override fun initFromConfigSecion(configSection: ConfigSection) {
        (configSection[sectionName] as LinkedHashMap<String, LinkedHashMap<String, *>>).forEach { (s, any) ->
            simpleConfig[s] = decode(any)
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