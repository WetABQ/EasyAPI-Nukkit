/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.config.encoder.external.serializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonSerializer : Serializer {

    override fun getSerializerId(): SerializerId = SerializerId.GsonSerializer

    override fun serialize(obj: Any): String = GsonBuilder().setPrettyPrinting().create().toJson(obj)

    override fun <T> deserialize(clazz: Class<T>, str: String): T = Gson().fromJson(str, clazz)

}
