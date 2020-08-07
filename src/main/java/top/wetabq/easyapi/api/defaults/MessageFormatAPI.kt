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