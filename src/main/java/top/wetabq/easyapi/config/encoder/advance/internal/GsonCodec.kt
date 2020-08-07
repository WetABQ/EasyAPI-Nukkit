package top.wetabq.easyapi.config.encoder.advance.internal

import com.google.gson.Gson

class GsonCodec<T>(private val clazz: Class<T>) :
    AdvanceConfigCodec<T> {
    override fun encode(obj: T): java.util.LinkedHashMap<String, *> = Gson().fromJson(Gson().toJson(obj, clazz), LinkedHashMap::class.java) as java.util.LinkedHashMap<String, *>

    override fun decode(map: LinkedHashMap<String, *>): T = Gson().fromJson(Gson().toJson(map, LinkedHashMap::class.java), clazz)

    override fun decode(any: Any): T = if (any is LinkedHashMap<*, *>) decode(any as LinkedHashMap<String, *>) else any as T
}