package top.wetabq.easyapi.api.default

import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.api.CommonDynamicIntegrateAPI
import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.config.default.SimpleConfig
import top.wetabq.easyapi.config.default.SimpleConfigEntry

@DisableNotRemoveAll
class SimpleConfigAPI(private val plugin: Plugin): CommonDynamicIntegrateAPI<SimpleConfigEntry<*>, SimpleConfigAPI>() {

    override fun addInterface(t: SimpleConfigEntry<*>): SimpleConfigAPI {
        SimpleConfig.addPath(t.path, plugin, t.value?:"")
        SimpleConfig.save()
        return this
    }

    override fun removeInterface(t: SimpleConfigEntry<*>): SimpleConfigAPI {
        SimpleConfig.removePath(t.path, plugin)
        SimpleConfig.save()
        return this
    }

    fun getPath(path: String): String {
        return SimpleConfig.getPath(path, plugin)
    }

    fun getPathValue(path: String): Any? {
        return SimpleConfig.getPathValue(path, plugin)
    }

    fun directlyGetPathValue(path: String): Any? {
        return SimpleConfig.directlyGetPathValue(path)
    }

    fun setPathValue(key: SimpleConfigEntry<*>) {
        SimpleConfig.setPathValue(key.path, plugin, key.value?:"")
    }



}