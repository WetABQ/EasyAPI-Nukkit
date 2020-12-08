/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.config.encoder.external

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import com.google.gson.Gson
import top.wetabq.easyapi.config.EasyConfig
import top.wetabq.easyapi.config.encoder.external.serializer.Serializer

abstract class AbstractExternalSerializerEasyConfig<T>(
    configName: String,
    plugin: Plugin,
    val serializer: Serializer,
    val clazzT: Class<T>,
    private val defaultValue: T
) : EasyConfig(configName, plugin), ExternalSerializerEasyConfig<T> {

    var configData: T? = null

    override fun initFromConfigSecion(configSection: ConfigSection) {
        if (configSection.containsKey("external")) {
            configData = serializer.deserialize(clazzT, configSection["external"] as String)
        } else {
            spawnDefault(configSection)
        }
    }

    override fun spawnDefault(configSection: ConfigSection) {
        configSection["external"] = ""
        configData = getDefaultValue()
        save()
    }

    override fun saveToConfigSection(configSection: ConfigSection) {
        configSection["external"] = serializer.serialize(configData?:"")
    }

    override fun safeGet(): T {
        return if (configData != null) {
            configData as T
        } else {
            spawnDefault(configSection)
            safeGet()
        }
    }

    override fun isEmpty(): Boolean {
        return super.isEmpty() && configData == null
    }

    fun getDefaultValue(): T = magicClone(defaultValue)

    private fun magicClone(obj: T): T {
        val stringProject = Gson().toJson(obj, clazzT)
        return Gson().fromJson<T>(stringProject, clazzT)
    }

}