package top.wetabq.easyapi.api.defaults

import top.wetabq.easyapi.api.SimpleIntegrateAPI

object MessageFormatAPI: SimpleIntegrateAPI {

    private val formatters = hashMapOf<String, HashMap<Class<*>, MessageFormatter<*>>>()
    private val simpleFormatters = arrayListOf<SimpleMessageFormatter>()

    fun format(message: String, vararg data: Any): String {
        var msg = message
        data.forEach {
            formatters.forEach { (_, fs) ->
                msg = fs[it.javaClass]?.parseFormat(message, it)?:msg
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

interface SimpleMessageFormatter {

    fun format(message: String): String

}