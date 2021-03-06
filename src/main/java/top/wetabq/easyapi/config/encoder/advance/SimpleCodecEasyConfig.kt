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
import com.google.gson.Gson
import top.wetabq.easyapi.config.encoder.advance.internal.AdvanceConfigCodec
import top.wetabq.easyapi.config.encoder.advance.internal.GsonCodec

/*@Deprecated(
    message = "Too many problems, give up maintenance. Files may be overwritten or be filled with null, data is not safe",
    replaceWith = ReplaceWith("GsonCodecEasyConfig")
)*/
abstract class SimpleCodecEasyConfig<T>(
    configName: String,
    val plugin: Plugin,
    val clazzT: Class<T>,
    private val defaultValue: T,
    private val codec: AdvanceConfigCodec<T> = GsonCodec(clazzT)
) : AdvanceCodecEasyConfig<T>(configName, plugin) {

    override fun encode(obj: T): LinkedHashMap<String, *> = codec.encode(obj)

    override fun decode(map: LinkedHashMap<String, *>): T = codec.decode(map)

    override fun decode(any: Any): T = codec.decode(any)

    fun safeGetData(key: String): T {
        if (!simpleConfig.containsKey(key)) simpleConfig[key] = getDefaultValue()
        return simpleConfig[key]?:getDefaultValue()
    }

    fun getDefaultValue(): T = magicClone(defaultValue)

    private fun magicClone(obj: T): T {
        val stringProject = Gson().toJson(obj, clazzT)
        return Gson().fromJson<T>(stringProject, clazzT)
    }


}