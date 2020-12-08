/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api.defaults

import cn.nukkit.Player
import top.wetabq.easyapi.api.SimpleIntegrateAPI

@Deprecated(
    message = "Too many problems, and not enough convenient, give up maintenance.",
    replaceWith = ReplaceWith("SimplePlaceholder")
)
object MessageFormatAPI: SimpleIntegrateAPI {

    private val formatters = hashMapOf<String, HashMap<Class<*>, MessageFormatter<*>>>()
    private val simpleFormatters = arrayListOf<SimpleMessageFormatter>()
    private val simplePlayerFormatters = arrayListOf<(String, Player) -> String>()

    fun format(message: String, vararg data: Any): String {
        var msg = message
        data.forEach {
            formatters.forEach { (_, fs) ->
                if (fs.containsKey(it.javaClass)) {
                    msg = fs[it.javaClass]?.parseFormat(msg, it) ?: msg
                }
            }
            if (it is Player) {
                simplePlayerFormatters.forEach { fs ->
                    msg = fs(msg, it)
                }
            }
        }
        simpleFormatters.forEach {
            msg = it.format(msg)
        }
        return msg
    }

    fun <T> registerFormatter(identifier: String, clazz: Class<T>, formatter: MessageFormatter<T>) {
        if (!formatters.containsKey(identifier)) {
            formatters[identifier] = hashMapOf()
        }
        formatters[identifier]?.let {
            it[clazz] = formatter
        }
    }

    fun registerSimpleFormatter(formatter: SimpleMessageFormatter) {
        simpleFormatters.add(formatter)
    }

    fun registerSimplePlayerFormatter(formatter: (String, Player) -> String) {
        simplePlayerFormatters.add(formatter)
    }

}

@Deprecated(
    message = "Too many problems, and not enough convenient, give up maintenance.",
    replaceWith = ReplaceWith("SimplePlaceholder")
)
interface MessageFormatter<T> {

    @Suppress("UNCHECKED_CAST")
    //@JvmDefault // sb kotlin
    fun parseFormat(message: String, data: Any): String {
        val tempData = data as? T
            ?: throw IllegalArgumentException("Invalid type ${data.javaClass.name} passed to this parser")
        return format(message, tempData)
    }

    fun format(message: String, data: T): String

}

@Deprecated(
    message = "Too many problems, and not enough convenient, give up maintenance.",
    replaceWith = ReplaceWith("SimplePlaceholder")
)
interface SimpleMessageFormatter {

    fun format(message: String): String

}