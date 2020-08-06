package top.wetabq.easyapi.config.encoder.external.serializer

interface Serializer {

    companion object {
        var Gson: Serializer = GsonSerializer()
        // var KotlinX: Serializer = KotlinXSerializer()
    }

    fun getSerializerId(): SerializerId


    fun serialize(obj: Any): String


    fun <T> deserialize(clazz: Class<T>, str: String): T
}

/**
 * TODO: Add serializerId as the prefix of the string in order to identify which provider the parser should use.
 */
enum class SerializerId (val id: Int) {

    GsonSerializer(0),
    // KotlinXSerializer(1)

}