package top.wetabq.easyapi.config.default

import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.config.encoder.CodecEasyConfig

object SimpleConfig : CodecEasyConfig<Any>(
    "integrateConfig",
    EasyAPI,
    "integrateConfig") {

    override fun encode(obj: Any): String = obj.toString()

    override fun decode(any: Any): Any = any

    /**
     * add config path
     * @param path use path name like "pluginName.catalog.configKey"
     * @param plugin your plugin
     */
    fun addPath(path: String, plugin: Plugin, defaultValue: Any) {
        simpleConfig[getPath(path, plugin)] = defaultValue
    }

    fun removePath(path: String, plugin: Plugin) {
        simpleConfig.remove(getPath(path, plugin))
    }

    fun getPathValue(path: String, plugin: Plugin): Any? = simpleConfig[getPath(path, plugin)]

    fun getPath(path: String, plugin: Plugin) : String = plugin.description.name + path

}