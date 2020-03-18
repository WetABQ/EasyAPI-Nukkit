package top.wetabq.easyapi.config.encoder

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import top.wetabq.easyapi.config.EasyConfig

abstract class CodecEasyConfig<T>(
    configName:String,
    plugin: Plugin
) : EasyConfig(configName, plugin), ConfigCodec<T> {

    var simpleConfig = linkedMapOf<String,T>()
    var sectionName = "config"

    @Suppress("UNCHECKED_CAST")
    override fun initFromConfigSecion(configSection: ConfigSection) {
        (configSection[sectionName] as LinkedHashMap<String, Any>).forEach { (s, any) ->
            simpleConfig[s] = decode(any)
        }
    }

    override fun spawnDefault(configSection: ConfigSection) {
        simpleConfig = linkedMapOf()
        sectionName = "config"
        configSection[sectionName] = simpleConfig
    }

    override fun saveToConfigSection(configSection: ConfigSection) {
        configSection.clear()
        val encodeMap = LinkedHashMap<String, Any>()
        simpleConfig.forEach { (key, obj) ->
            encodeMap[key] = encode(obj)
        }
        configSection[sectionName] = encodeMap
    }

}