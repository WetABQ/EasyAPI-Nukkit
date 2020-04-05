package top.wetabq.easyapi.config.defaults

import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.config.encoder.CodecEasyConfig

object SimpleConfig : CodecEasyConfig<Any>(
    "integrateConfig",
    EasyAPI.INSTANCE) {

    override fun encode(obj: Any): String = obj.toString()

    override fun decode(any: Any): Any = any

    /**
     * add config path
     * @param path use path name like "pluginName.catalog.configKey"
     * @param plugin your plugin
     */
    fun addPath(path: String, plugin: Plugin, defaultValue: Any) {
        if (!simpleConfig.containsKey(getPath(path, plugin))) {
            setPathValue(path, plugin, defaultValue)
        }
    }

    fun removePath(path: String, plugin: Plugin) {
        simpleConfig.remove(getPath(path, plugin))
    }

    fun setPathValue(path: String, plugin: Plugin, value: Any) {
        simpleConfig[getPath(path, plugin)] = value
        save()
    }

    fun getPathValue(path: String, plugin: Plugin): Any? = simpleConfig[getPath(path, plugin)]

    fun directlyGetPathValue(path: String): Any? = simpleConfig[path]

    fun getPath(path: String, plugin: Plugin) : String = plugin.description.name + if(path.startsWith(".")) path else ".$path"

}