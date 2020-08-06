package top.wetabq.easyapi.config

import cn.nukkit.plugin.Plugin
import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection
import java.util.concurrent.locks.ReentrantLock

abstract class EasyConfig(configName:String, plugin: Plugin) : IEasyConfig {

    private var config: Config = Config("${plugin.dataFolder}/$configName.yml", Config.YAML)
    protected var configSection: ConfigSection

    protected val configLock = ReentrantLock()

    init {
        this.configSection = config.rootSection
    }

    override fun init() {
        configLock.lock()
        try {
            if (isEmpty()) spawnDefaultConfig() else initFromConfigSecion(configSection)
        } finally {
            configLock.unlock()
        }
    }

    override fun spawnDefaultConfig() {
        configLock.lock()
        try {
            if (isEmpty()) {
                spawnDefault(configSection)
            }
            init()
            save()
        } finally {
            configLock.unlock()
        }
    }

    override fun save() {
        configLock.lock()
        try {
            if (!isEmpty()) {
                saveToConfigSection(configSection)
                config.setAll(configSection)
                config.save()
            } else {
                spawnDefaultConfig()
            }
        } finally {
            configLock.unlock()
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