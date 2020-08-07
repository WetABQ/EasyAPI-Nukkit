package top.wetabq.easyapi.config.encoder.external

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.ConfigSection
import com.google.gson.Gson
import top.wetabq.easyapi.config.EasyConfig
import top.wetabq.easyapi.config.encoder.external.serializer.Serializer

abstract class AbstractExternalSerializerEasyConfig<T>(
    configName: String,
    plugin: Plugin,
    val serializer: Serializer,
    val clazzT: Class<T>,
    private val defaultValue: T
) : EasyConfig(configName, plugin), ExternalSerializerEasyConfig<T> {

    var configData: T? = null

    override fun initFromConfigSecion(configSection: ConfigSection) {
        if (configSection.containsKey("external")) {
            configData = serializer.deserialize(clazzT, configSection["external"] as String)
        } else {
            spawnDefault(configSection)
        }
    }

    override fun spawnDefault(configSection: ConfigSection) {
        configSection["external"] = ""
        configData = getDefaultValue()
        save()
    }

    override fun saveToConfigSection(configSection: ConfigSection) {
        configSection["external"] = serializer.serialize(configData?:"")
    }

    override fun safeGet(): T {
        return if (configData != null) {
            configData as T
        } else {
            spawnDefault(configSection)
            safeGet()
        }
    }

    override fun isEmpty(): Boolean {
        return super.isEmpty() && configData == null
    }

    fun getDefaultValue(): T = magicClone(defaultValue)

    private fun magicClone(obj: T): T {
        val stringProject = Gson().toJson(obj, clazzT)
        return Gson().fromJson<T>(stringProject, clazzT)
    }

}