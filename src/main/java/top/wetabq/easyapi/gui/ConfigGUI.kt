/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

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
 * WARN: Use the lib gui.jar @author by Him188
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
            if (finalElement != "%NONE%" && !simpleCodecEasyConfig.clazzT.getDeclaredField(elementName).isAnnotationPresent(IgnoreElement::class.java)) {
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
                val arrayParam = arrayListOf<Any?>()
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
                if (arrayParam.toArray().size < clazz.constructors[0].parameterTypes.size) { //sbKotlin
                    arrayParam.add(1)
                    arrayParam.add(null)
                }

                val newDataObj = clazz.constructors[0].newInstance(*autoNumberType(arrayParam, clazz.constructors[0].parameterTypes)) as T
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
            value.contains(Regex("""^-?\d+\.\d+""")) -> value.toDouble()
            value.contains(Regex("(true|false)")) -> value.toBoolean()
            value.contains(Regex("""^[+-]?\d+""")) -> value.toInt()
            else -> value
        }
    }

    private fun autoNumberType(arrayParam: ArrayList<Any?>, constructorArray: Array<Class<*>>): Array<Any?> {
        val finalArrayParam = arrayListOf<Any?>()
        arrayParam.forEachIndexed { index, p ->
            if (p is Int) {
                if (constructorArray[index].name.toLowerCase().contains("double")) {
                    finalArrayParam.add(p.toDouble())
                } else {
                    finalArrayParam.add(p)
                }
            } else if (p is Double) {
                if (constructorArray[index].name.toLowerCase().contains("int")) {
                    finalArrayParam.add(p.toInt())
                } else {
                    finalArrayParam.add(p)
                }
            } else {
                finalArrayParam.add(p)
            }
        }
        return finalArrayParam.toArray()
    }

}
