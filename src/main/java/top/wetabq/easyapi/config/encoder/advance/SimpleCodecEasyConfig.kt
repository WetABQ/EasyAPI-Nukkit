package top.wetabq.easyapi.config.encoder.advance

import cn.nukkit.plugin.Plugin
import com.google.gson.Gson
import top.wetabq.easyapi.config.encoder.advance.internal.AdvanceConfigCodec
import top.wetabq.easyapi.config.encoder.advance.internal.ReflectionConfigCodec

/*@Deprecated(
    message = "Too many problems, give up maintenance. Files may be overwritten or be filled with null, data is not safe",
    replaceWith = ReplaceWith("GsonCodecEasyConfig")
)*/
abstract class SimpleCodecEasyConfig<T>(
    configName: String,
    val plugin: Plugin,
    val clazzT: Class<T>,
    private val defaultValue: T,
    private val codec: AdvanceConfigCodec<T> = ReflectionConfigCodec(
        clazzT
    )
) : AdvanceCodecEasyConfig<T>(configName, plugin) {

    override fun encode(obj: T): LinkedHashMap<String, *> = codec.encode(obj)

    override fun decode(map: LinkedHashMap<String, *>): T = codec.decode(map)

    override fun decode(any: Any): T = codec.decode(any)

    fun safeGetData(key: String): T {
        if (!simpleConfig.containsKey(key)) simpleConfig[key] = getDefaultValue()
        return simpleConfig[key]?:getDefaultValue()
    }

    fun getDefaultValue(): T = magicClone(defaultValue)

    private fun magicClone(obj: T): T {
        val stringProject = Gson().toJson(obj, clazzT)
        return Gson().fromJson<T>(stringProject, clazzT)
    }


}