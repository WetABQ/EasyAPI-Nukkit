package top.wetabq.easyapi.module.default

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.default.CommandAPI
import top.wetabq.easyapi.api.default.ConfigAPI
import top.wetabq.easyapi.api.default.SimpleConfigAPI
import top.wetabq.easyapi.command.default.EasyAPICommand
import top.wetabq.easyapi.config.default.SimpleConfig
import top.wetabq.easyapi.config.default.SimpleConfigEntry
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
        EasyAPI,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1,0,0)
    )


    override fun moduleRegister() {
        this.registerAPI(SIMPLE_COMMAND, CommandAPI())
            .add(EasyAPICommand)
        this.registerAPI(SIMPLE_CONFIG, ConfigAPI())
            .add(SimpleConfig)
        this.registerAPI(EASY_API_CONFIG, SimpleConfigAPI(this.getModuleInfo().moduleOwner))
            .add(SimpleConfigEntry(TITLE_PATH, "&c[&eEasy&aAPI&c]".color()))
        (this.getIntegrateAPI(EASY_API_CONFIG) as? SimpleConfigAPI)?.let {
            EasyAPI.TITLE = it.getPathValue(TITLE_PATH) as String
        }

    }

    override fun moduleDisable() {

    }

}