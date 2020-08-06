package top.wetabq.easyapi.config.encoder.external.serializer

import com.google.gson.Gson

class GsonSerializer : Serializer {

    override fun getSerializerId(): SerializerId = SerializerId.GsonSerializer

    override fun serialize(obj: Any): String = Gson().toJson(obj)

    override fun <T> deserialize(clazz: Class<T>, str: String): T = Gson().fromJson(str, clazz)

}
