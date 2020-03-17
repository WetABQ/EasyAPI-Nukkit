package top.wetabq.easyapi.api.default

import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.config.default.SimpleConfig
import top.wetabq.easyapi.config.default.SimpleConfigEntry

@DisableNotRemoveAll
class SimpleConfigAPI(private val plugin: Plugin): DynamicIntegrateAPI<SimpleConfigEntry<*>, SimpleConfigAPI> {

    private val simpleConfigEntryList = arrayListOf<SimpleConfigEntry<*>>()

    override fun add(t: SimpleConfigEntry<*>): SimpleConfigAPI {
        SimpleConfig.addPath(t.path, plugin, t.value?:"")
        SimpleConfig.save()
        return this
    }

    override fun remove(t: SimpleConfigEntry<*>): SimpleConfigAPI {
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

    override fun getAll(): Collection<SimpleConfigEntry<*>> = simpleConfigEntryList

    override fun removeAll() {
        simpleConfigEntryList.forEach { t -> remove(t) }
    }


}