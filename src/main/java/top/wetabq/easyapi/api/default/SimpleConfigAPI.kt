package top.wetabq.easyapi.api.default

import top.wetabq.easyapi.api.DisableNotRemoveAll
import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.config.default.SimpleConfig
import top.wetabq.easyapi.config.default.SimpleConfigEntry
import top.wetabq.easyapi.module.EasyAPIModule
import top.wetabq.easyapi.module.ModuleRegistry

@DisableNotRemoveAll
class SimpleConfigAPI(private val module: EasyAPIModule): DynamicIntegrateAPI<SimpleConfigEntry<*>> {

    private val simpleConfigEntryList = arrayListOf<SimpleConfigEntry<*>>()

    override fun add(t: SimpleConfigEntry<*>): ModuleRegistry {
        SimpleConfig.addPath(t.path, module.getModuleInfo().moduleOwner, t.value?:"")
        SimpleConfig.save()
        return module.getModuleRegistry()
    }

    override fun remove(t: SimpleConfigEntry<*>): ModuleRegistry {
        SimpleConfig.removePath(t.path, module.getModuleInfo().moduleOwner)
        SimpleConfig.save()
        return module.getModuleRegistry()
    }

    override fun getAll(): Collection<SimpleConfigEntry<*>> = simpleConfigEntryList

    override fun removeAll() {
        simpleConfigEntryList.forEach { t -> remove(t) }
    }


}