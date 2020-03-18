package top.wetabq.easyapi.config.encoder

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import top.wetabq.easyapi.config.EasyConfig

@Suppress("SENSELESS_COMPARISON")
abstract class CodecEasyConfig<T>(
    configName:String,
    plugin: Plugin
) : EasyConfig(configName, plugin), ConfigCodec<T> {

    var simpleConfig = linkedMapOf<String,T>()

    @Suppress("UNCHECKED_CAST")
    override fun initFromConfigSecion(configSection: ConfigSection) {
        // if (simpleConfig == null) simpleConfig = linkedMapOf()
        (configSection as LinkedHashMap<String, Any>).forEach { (s, any) ->
            simpleConfig[s] = decode(any)
        }
    }

    override fun spawnDefault(configSection: ConfigSection) {
        // if (simpleConfig == null) simpleConfig = linkedMapOf()
        configSection.putAll(simpleConfig)
        configSection["notEmpty"] = "true"
    }

    override fun saveToConfigSection(configSection: ConfigSection) {
        configSection.clear()
        val encodeMap = LinkedHashMap<String, Any>()
        simpleConfig.forEach { (key, obj) ->
            encodeMap[key] = encode(obj)
        }
        configSection.putAll(encodeMap)
    }

    override fun isEmpty(): Boolean {
        // if (simpleConfig == null) simpleConfig = linkedMapOf()
        return simpleConfig.isEmpty() && configSection.isEmpty()
    }

}