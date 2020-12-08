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
import com.google.gson.TypeAdapter
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.util.*


class DataTypeAdaptor internal constructor(private val gson: Gson) : TypeAdapter<Any>() {

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Any? {
        return when (`in`.peek()) {
            JsonToken.BEGIN_ARRAY -> {
                val list: MutableList<Any?> = ArrayList()
                `in`.beginArray()
                while (`in`.hasNext()) {
                    list.add(read(`in`))
                }
                `in`.endArray()
                list
            }
            JsonToken.BEGIN_OBJECT -> {
                val map: MutableMap<String, Any?> =
                    LinkedTreeMap()
                `in`.beginObject()
                while (`in`.hasNext()) {
                    map[`in`.nextName()] = read(`in`)
                }
                `in`.endObject()
                map
            }
            JsonToken.STRING -> `in`.nextString()
            JsonToken.NUMBER -> {
                val numberStr: String = `in`.nextString()
                if (numberStr.contains(".") || numberStr.contains("e")
                    || numberStr.contains("E")
                ) {
                    return numberStr.toDouble()
                }
                if (numberStr.toLong() <= Int.MAX_VALUE) {
                    numberStr.toInt()
                } else numberStr.toLong()
            }
            JsonToken.BOOLEAN -> `in`.nextBoolean()
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            else -> throw IllegalStateException()
        }
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: Any?) {

    }

}