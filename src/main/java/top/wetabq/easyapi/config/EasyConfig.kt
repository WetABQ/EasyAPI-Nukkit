package top.wetabq.easyapi.config

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection

abstract class EasyConfig(configName:String, plugin: Plugin) : IEasyConfig {

    private var config: Config = Config("${plugin.dataFolder}/$configName.yml", Config.YAML)
    private var configSection: ConfigSection

    init {
        this.configSection = config.rootSection
        init0()
    }

    private fun init0() {
        init()
    }

    override fun init() {
        if (isEmpty()) spawnDefaultConfig() else initFromConfigSecion(configSection)
    }

    override fun spawnDefaultConfig() {
        if (isEmpty()) {
            spawnDefault(configSection)
        }
        init()
        save()
    }

    override fun save() {
        if (!isEmpty())  {
            saveToConfigSection(configSection)
            config.setAll(configSection)
            config.save()
        } else {
            spawnDefaultConfig()
        }
    }

    override fun reload() {
        config.reload()
        this.configSection = config.rootSection
        init()
    }

    override fun isEmpty(): Boolean {
        return configSection.isEmpty()
    }

    protected abstract fun initFromConfigSecion(configSection: ConfigSection)

    protected abstract fun spawnDefault(configSection: ConfigSection)

    protected abstract fun saveToConfigSection(configSection: ConfigSection)

}