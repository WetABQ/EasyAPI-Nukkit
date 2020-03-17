package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.SimpleIntegrateAPI

object MessageFormatAPI: SimpleIntegrateAPI {

    val formatters = hashMapOf<Class<*>, MessageFormatter<*>>()

    fun format(message: String, vararg data: Any): String {
        var msg = message
        data.forEach {
            msg = formatters[it.javaClass]?.parseFormat(message, it)?:msg
        }
        return msg
    }

}

interface MessageFormatter<T> {

    @Suppress("UNCHECKED_CAST")
    fun parseFormat(message: String, data: Any): String {
        val tempData = data as? T
            ?: throw IllegalArgumentException("Invalid type ${data.javaClass.name} passed to this parser")
        return format(message, tempData)
    }

    fun format(message: String, data: T): String

}