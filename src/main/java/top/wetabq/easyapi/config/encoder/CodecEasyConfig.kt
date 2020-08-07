/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

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