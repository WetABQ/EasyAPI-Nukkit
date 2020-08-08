/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.config.encoder.advance

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import top.wetabq.easyapi.config.encoder.CodecEasyConfig
import top.wetabq.easyapi.config.encoder.advance.internal.AdvanceConfigCodec

abstract class AdvanceCodecEasyConfig<T>(
    configName:String,
    plugin: Plugin
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
}