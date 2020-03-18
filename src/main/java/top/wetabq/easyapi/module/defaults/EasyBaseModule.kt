package top.wetabq.easyapi.module.defaults

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.CommandAPI
import top.wetabq.easyapi.api.defaults.ConfigAPI
import top.wetabq.easyapi.api.defaults.SimpleConfigAPI
import top.wetabq.easyapi.command.defaults.EasyAPICommand
import top.wetabq.easyapi.config.defaults.SimpleConfig
import top.wetabq.easyapi.config.defaults.SimpleConfigEntry
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule
import top.wetabq.easyapi.utils.color

object EasyBaseModule: SimpleEasyAPIModule() {

    const val MODULE_NAME = "EasyBaseModule"
    const val AUTHOR = "WetABQ"

    const val SIMPLE_COMMAND = "simpleCommand"
    const val SIMPLE_CONFIG = "simpleConfig"
    const val EASY_API_CONFIG = "easyAPIConfig"

    const val TITLE_PATH = "title"

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI.INSTANCE,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1,0,0)
    )


    override fun moduleRegister() {
        this.registerAPI(SIMPLE_COMMAND, CommandAPI())
            .add(EasyAPICommand)
        this.registerAPI(SIMPLE_CONFIG, ConfigAPI())
            .add(SimpleConfig)
        SimpleConfig.init()
        val easyAPIConfig = this.registerAPI(EASY_API_CONFIG, SimpleConfigAPI(this.getModuleInfo().moduleOwner))
            .add(SimpleConfigEntry(TITLE_PATH, "&c[&eEasy&aAPI&c]".color()))

        EasyAPI.TITLE = easyAPIConfig.getPathValue(TITLE_PATH) as String

    }

    override fun moduleDisable() {

    }

}