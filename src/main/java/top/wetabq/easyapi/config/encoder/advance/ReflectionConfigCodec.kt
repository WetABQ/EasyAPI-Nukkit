package top.wetabq.easyapi.config.encoder.advance

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
                fieldValue
            } else {
                reflectEncode(fieldValue, fieldValue::class.java)
            }
        }
        return encodeMap
    }

    private fun reflectDecode(map: LinkedHashMap<String, *>, clazz: Class<out Any>): Any {
        val constructor = clazz.constructors[0]
        val arrayList = arrayListOf<Any>()
        val mapValueList = map.values.toList()
        constructor.parameterTypes.forEachIndexed { index, it ->
            if (isJavaClass(it)) {
                arrayList.add(mapValueList[index])
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