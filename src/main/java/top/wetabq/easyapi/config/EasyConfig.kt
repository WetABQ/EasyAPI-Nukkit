/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

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