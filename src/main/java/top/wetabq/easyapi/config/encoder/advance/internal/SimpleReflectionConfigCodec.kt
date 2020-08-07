/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.config.encoder.advance.internal


@Suppress("UNCHECKED_CAST")
class SimpleReflectionConfigCodec<T>(private val clazz: Class<T>) :
    AdvanceConfigCodec<T> {

    override fun encode(obj: T): LinkedHashMap<String, *> {
        return reflectEncode(obj as Any, clazz as Class<out Any>)
    }

    override fun decode(map: LinkedHashMap<String, *>): T {
        return reflectDecode(map, clazz as Class<out Any>) as T
    }

    override fun decode(any: Any): T {
        return if (any is LinkedHashMap<*, *>) decode(any as LinkedHashMap<String, *>) else any as T
    }

    private fun reflectEncode(obj: Any, clazz: Class<out Any>): LinkedHashMap<String, *> {
        val encodeMap = linkedMapOf<String, Any>()
        clazz.declaredFields.forEach {
            it.isAccessible = true
            val fieldValue = it.get(obj)
            encodeMap[it.name] = fieldValue
        }
        return encodeMap
    }

    private fun reflectDecode(map: LinkedHashMap<String, *>, clazz: Class<out Any>): Any {
        val constructor = clazz.constructors[0]
        val obj = constructor.newInstance()
        clazz.declaredFields.forEachIndexed { index, it ->
            clazz.fields[index].set(obj, map[it.name])
        }
        return obj
    }

    private fun isJavaClass(clz: Class<*>): Boolean {
        return (clz != null && clz.classLoader == null) || clz.`package`.name.startsWith("kotlin") || clz.`package`.name.startsWith("java")
    }


}