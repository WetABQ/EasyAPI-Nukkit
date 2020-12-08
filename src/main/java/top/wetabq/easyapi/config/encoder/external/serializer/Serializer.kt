/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.config.encoder.external.serializer

interface Serializer {

    companion object {
        var Gson: Serializer = GsonSerializer()
        // var KotlinX: Serializer = KotlinXSerializer()
    }

    fun getSerializerId(): SerializerId


    fun serialize(obj: Any): String


    fun <T> deserialize(clazz: Class<T>, str: String): T
}

/**
 * TODO: Add serializerId as the prefix of the string in order to identify which provider the parser should use.
 */
enum class SerializerId (val id: Int) {

    GsonSerializer(0),
    // KotlinXSerializer(1)

}