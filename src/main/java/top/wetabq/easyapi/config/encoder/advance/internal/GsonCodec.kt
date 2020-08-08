/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.config.encoder.advance.internal

import com.google.gson.Gson

class GsonCodec<T>(private val clazz: Class<T>) :
    AdvanceConfigCodec<T> {
    override fun encode(obj: T): java.util.LinkedHashMap<String, *> = Gson().fromJson(Gson().toJson(obj, clazz), LinkedHashMap::class.java) as java.util.LinkedHashMap<String, *>

    override fun decode(map: LinkedHashMap<String, *>): T = Gson().fromJson(Gson().toJson(map, LinkedHashMap::class.java), clazz)

    override fun decode(any: Any): T = if (any is LinkedHashMap<*, *>) decode(any as LinkedHashMap<String, *>) else any as T
}