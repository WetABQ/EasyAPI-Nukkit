package top.wetabq.easyapi.gui

import cn.nukkit.Player
import cn.nukkit.form.element.ElementInput
import cn.nukkit.form.element.ElementToggle
import cn.nukkit.form.response.FormResponseCustom
import cn.nukkit.form.window.FormWindow
import moe.him188.gui.window.ResponsibleFormWindowCustom
import top.wetabq.easyapi.config.encoder.advance.SimpleCodecEasyConfig
import top.wetabq.easyapi.utils.color
import java.lang.reflect.Field
import kotlin.math.min

/**
 * WARN: Use the lib gui.jar by Him188
 */
class ConfigGUI<T>(
    private val simpleCodecEasyConfig: SimpleCodecEasyConfig<T>,
    private val obj: T,
    private val key: String,
    guiTitle: String = "",
    parent: FormWindow? = null
) : ResponsibleFormWindowCustom(guiTitle) {

    private var translatedMap = linkedMapOf<String, String>()

    init {
        setParent(parent)
    }

    fun init() {
        addElement(ElementInput("Id", key, key))
        simpleCodecEasyConfig.encode(obj).forEach { (elementName, value) ->
            var finalElement = elementName
            if (translatedMap.isNotEmpty()) finalElement = translatedMap[elementName] ?: elementName
            if (finalElement != "%NONE%") {
                if (value is Boolean) {
                    addElement(ElementToggle(finalElement, value))
                } else {
                    addElement(ElementInput(finalElement, value.toString(), value.toString()))
                }
            }
        }
    }

    fun setTranslateMap(map: LinkedHashMap<String, String>) {
        translatedMap = map
    }

    @Suppress("UNCHECKED_CAST")
    override fun onClicked(response: FormResponseCustom, player: Player) {
        response.responses[0]?.let { id ->
            val params = arrayListOf<Any>()
            response.responses
                .also { it.remove(0) } // Remove Id(key)
                .toSortedMap(Comparator { o1, o2 -> min(o1, o2) }).values // sort params order
                .forEach { value -> params.add(value) }
            try {
                val clazz = simpleCodecEasyConfig.clazzT
                val arrayParam = arrayListOf<Any>()
                params.forEach { param -> arrayParam.add(autoType(param.toString())) }
                if (translatedMap.isNotEmpty()) {
                    translatedMap.forEach { (key, value) ->
                        if (value == "%NONE%") {
                            lateinit var field: Field
                            clazz.declaredFields.forEach { if (it.name == key) field = it }
                            field.isAccessible = true
                            arrayParam.add(field.get(obj))
                        }
                    }
                }
                val newDataObj = clazz.constructors[0].newInstance(*arrayParam.toArray()) as T
                if (id is String) {
                    player.sendMessage("&e${simpleCodecEasyConfig.plugin.name} > &aSave successfully".color())
                    simpleCodecEasyConfig.simpleConfig[id] = newDataObj
                    simpleCodecEasyConfig.save()
                } else {
                    player.sendMessage("&e${simpleCodecEasyConfig.plugin.name} > &cError getting instance object via reflection".color())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                player.sendMessage("&e${simpleCodecEasyConfig.plugin.name} > &cError getting instance object via reflection".color())
            }
        }
    }

    override fun onClosed(player: Player) {
        if (parent != null) goBack(player)
    }

    private fun autoType(value: String): Any {
        return when {
            value.contains(".") -> value.toDouble()
            value.contains(Regex("(true|false)")) -> value.toBoolean()
            value.contains(Regex("""^[+-]?[0-9]\d*""")) -> value.toInt()
            else -> value
        }
    }

}
