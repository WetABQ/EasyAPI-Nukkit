package top.wetabq.easyapi.config

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection

abstract class EasyConfig(configName:String, plugin: Plugin) : IEasyConfig {

    var config: Config = Config("${plugin.dataFolder}/$configName.yml", Config.YAML)
    var configSection: ConfigSection

    init {
        this.configSection = config.rootSection
        init0()
    }

    fun init0() {
        init()
    }

    override fun init() {
        if (isEmpty()) spawnDefaultConfig()
    }


    override fun save() {
        if (isEmpty()) spawnDefaultConfig()
        config.setAll(configSection)
        config.save()
    }

    fun isEmpty(): Boolean {
        return configSection.isEmpty()
    }

}