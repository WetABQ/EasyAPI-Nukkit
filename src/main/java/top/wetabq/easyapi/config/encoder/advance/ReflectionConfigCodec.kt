package top.wetabq.easyapi.config.encoder.advance

import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
class ReflectionConfigCodec<T>(private val clazz: Class<T>) :
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
            encodeMap[it.name] = if (isJavaClass(fieldValue::class.java)) {
                when (fieldValue) {
                    is LinkedHashMap<*, *> -> reflectEncodeMap(fieldValue as LinkedHashMap<String, *>)
                    is ArrayList<*> -> reflectEncodeList(fieldValue)
                    else -> fieldValue
                }

            } else {
                reflectEncode(fieldValue, fieldValue::class.java)
            }
        }
        return encodeMap
    }

    private fun reflectEncodeMap(obj: LinkedHashMap<String, *>): LinkedHashMap<String, *> {
        val encodeMap = linkedMapOf<String, Any>()
        obj.forEach { (s, any) ->
            when {
                any is LinkedHashMap<*, *> -> encodeMap[s] = reflectEncodeMap(any as LinkedHashMap<String, *>)
                any is ArrayList<*> -> encodeMap[s] = reflectEncodeList(any)
                isJavaClass(any.javaClass) -> encodeMap[s] = any
                else -> {
                    encodeMap[s] = reflectEncode(any, any.javaClass)
                    /*any.javaClass.declaredFields.forEach { field ->
                        val fieldValue = field.get(any)
                        when {
                            fieldValue is LinkedHashMap<*, *> -> (encodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncodeMap(fieldValue as LinkedHashMap<String, *>)
                            fieldValue is List<*> -> (encodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncodeList(fieldValue)
                            isJavaClass(field.type) -> (encodeMap[s] as LinkedHashMap<String, Any>)[field.name] = fieldValue
                            else -> (encodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncode(fieldValue, field.type)
                        }
                    }*/
                }
            }
        }
        return encodeMap
    }

    private fun reflectEncodeList(obj: ArrayList<*>): ArrayList<*> {
        val encodeList = arrayListOf<Any>()
        obj.forEach { a ->
            a?.let { any ->
                when {
                    any is LinkedHashMap<*, *> -> encodeList.add(reflectEncodeMap(any as LinkedHashMap<String, *>))
                    any is ArrayList<*> -> encodeList.add(reflectEncodeList(any))
                    isJavaClass(any.javaClass) -> encodeList.add(any)
                    else -> {
                        val newObjMap = reflectEncode(any, any.javaClass)
                        /*any.javaClass.declaredFields.forEach { field ->
                            val fieldValue = field.get(any)
                            when {
                                fieldValue is LinkedHashMap<*, *> -> newObjMap[field.name] = reflectEncodeMap(fieldValue as LinkedHashMap<String, *>)
                                fieldValue is List<*> -> newObjMap[field.name] = reflectEncodeList(fieldValue)
                                isJavaClass(field.type) -> newObjMap[field.name] = fieldValue
                                else -> newObjMap[field.name] = reflectEncode(fieldValue, field.type)
                            }
                        }*/
                        encodeList.add(newObjMap)
                    }
                }
            }
        }
        return encodeList
    }

    private fun reflectDecodeMap(obj: LinkedHashMap<String, *>, valueType: ParameterizedType): LinkedHashMap<String, *> {
        val decodeMap = linkedMapOf<String, Any>()
        obj.forEach { (s, any) ->
            when {
                valueType.actualTypeArguments[1] is ParameterizedType && (valueType.actualTypeArguments[1] as ParameterizedType).typeName.contains("LinkedHashMap") -> decodeMap[s] = reflectDecodeMap(any as LinkedHashMap<String, *>, valueType.actualTypeArguments[1] as ParameterizedType)
                valueType.actualTypeArguments[1] is ParameterizedType && (valueType.actualTypeArguments[1] as ParameterizedType).typeName.contains("ArrayList") -> decodeMap[s] = reflectDecodeList(any as ArrayList<*>, valueType.actualTypeArguments[1] as ParameterizedType)
                isJavaClass(valueType.actualTypeArguments[0] as Class<*>) -> decodeMap[s] = any
                else -> {
                    decodeMap[s] = reflectDecode(any as LinkedHashMap<String, *>, valueType.actualTypeArguments[1] as Class<*>)
                    /*any.javaClass.declaredFields.forEach { field ->
                        val fieldValue = field.get(any)
                        when {
                            fieldValue is LinkedHashMap<*, *> -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncodeMap(fieldValue as LinkedHashMap<String, *>)
                            fieldValue is List<*> -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncodeList(fieldValue)
                            isJavaClass(field.type) -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = fieldValue
                            else -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncode(fieldValue, field.type)
                        }
                    }*/
                }
            }
        }
        return decodeMap
    }

    private fun reflectDecodeList(obj: ArrayList<*>, valueType: ParameterizedType): ArrayList<*> {
        val decodeList = arrayListOf<Any>()
        obj.forEach { a ->
            a?.let { any ->
                when {
                    valueType.actualTypeArguments[0] is ParameterizedType && (valueType.actualTypeArguments[0] as ParameterizedType).rawType is LinkedHashMap<*, *> -> decodeList.add(reflectDecodeMap(any as LinkedHashMap<String, *>, valueType.actualTypeArguments[0] as ParameterizedType))
                    valueType.actualTypeArguments[0] is ParameterizedType && (valueType.actualTypeArguments[0] as ParameterizedType).rawType is ArrayList<*> -> decodeList.add(reflectDecodeList(any as ArrayList<*>, valueType.actualTypeArguments[0] as ParameterizedType))
                    isJavaClass(valueType.actualTypeArguments[0] as Class<*>) -> decodeList.add(any)
                    else -> {
                        decodeList.add(reflectDecode(any as LinkedHashMap<String, *>, valueType.actualTypeArguments[0] as Class<*>))
                        /*any.javaClass.declaredFields.forEach { field ->
                        val fieldValue = field.get(any)
                        when {
                            fieldValue is LinkedHashMap<*, *> -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncodeMap(fieldValue as LinkedHashMap<String, *>)
                            fieldValue is List<*> -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncodeList(fieldValue)
                            isJavaClass(field.type) -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = fieldValue
                            else -> (decodeMap[s] as LinkedHashMap<String, Any>)[field.name] = reflectEncode(fieldValue, field.type)
                        }
                    }*/
                    }
                }
            }
        }
        return decodeList
    }


    private fun reflectDecode(map: LinkedHashMap<String, *>, clazz: Class<out Any>): Any {
        val constructor = clazz.constructors[0]
        val arrayList = arrayListOf<Any>()
        val mapValueList = map.values.toList()
        constructor.parameterTypes.forEachIndexed { index, it ->
            if (isJavaClass(it)) {
                when {
                    Map::class.java.isAssignableFrom(it) -> arrayList.add(reflectDecodeMap(mapValueList[index] as LinkedHashMap<String, *>, clazz.declaredFields[index].genericType as ParameterizedType))
                    List::class.java.isAssignableFrom(it) -> arrayList.add(reflectDecodeList(mapValueList[index] as ArrayList<*>, (clazz.declaredFields[index].genericType as ParameterizedType)))
                    else -> arrayList.add(mapValueList[index])
                }
            } else {
                arrayList.add(reflectDecode(mapValueList[index] as LinkedHashMap<String, *>, it))
            }
        }
        return constructor.newInstance(*arrayList.toArray())
    }

    private fun isJavaClass(clz: Class<*>): Boolean {
        return (clz != null && clz.classLoader == null) || clz.`package`.name.startsWith("kotlin") || clz.`package`.name.startsWith("java")
    }



}