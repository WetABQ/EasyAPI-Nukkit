package top.wetabq.easyapi.module.defaults

import cn.nukkit.scheduler.AsyncTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.AsyncTaskAPI
import top.wetabq.easyapi.api.defaults.CommandAPI
import top.wetabq.easyapi.api.defaults.ConfigAPI
import top.wetabq.easyapi.api.defaults.SimpleConfigAPI
import top.wetabq.easyapi.command.defaults.EasyAPICommand
import top.wetabq.easyapi.config.defaults.SimpleConfig
import top.wetabq.easyapi.config.defaults.SimpleConfigEntry
import top.wetabq.easyapi.module.EasyAPIModuleManager
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule
import top.wetabq.easyapi.placeholder.PlaceholderExpansion
import top.wetabq.easyapi.placeholder.SimplePlaceholder
import top.wetabq.easyapi.utils.color

object EasyBaseModule: SimpleEasyAPIModule() {

    const val MODULE_NAME = "EasyBaseModule"
    const val AUTHOR = "WetABQ"

    const val SIMPLE_COMMAND = "simpleCommand"
    const val SIMPLE_CONFIG = "simpleConfig"
    const val EASY_API_CONFIG = "easyAPIConfig"
    const val UNREGISTER_MODULE_TASK = "unregisterModuleTask"

    const val TITLE_PATH = "title"
    const val MODULE_PATH = "module"

    const val PLACEHOLDER_EASYAPI = "easyapi"

    lateinit var easyAPIPlaceholder: PlaceholderExpansion
    lateinit var easyAPIConfig: SimpleConfigAPI

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI.INSTANCE,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1,0,0)
    )

    fun getTitle(): String = easyAPIConfig.getPathValue(TITLE_PATH).toString()
    fun setTitle(new: String) = easyAPIConfig.setPathValue(SimpleConfigEntry(TITLE_PATH, new))

    override fun moduleRegister() {
        this.registerAPI(SIMPLE_COMMAND, CommandAPI())
            .add(EasyAPICommand)
        this.registerAPI(SIMPLE_CONFIG, ConfigAPI())
            .add(SimpleConfig)
        SimpleConfig.init()

        easyAPIConfig = this.registerAPI(EASY_API_CONFIG, SimpleConfigAPI(this.getModuleInfo().moduleOwner))
            .add(SimpleConfigEntry(TITLE_PATH, "&c[&eEasy&aAPI&c]".color()))

        this.registerAPI(UNREGISTER_MODULE_TASK, AsyncTaskAPI(this.getModuleInfo().moduleOwner))
            .add(object: AsyncTask() {
                override fun onRun() {
                    EasyAPIModuleManager.getAllModule().forEach { (info, module) ->
                        val moduleSwitchPath = "$MODULE_PATH.${info.moduleOwner.name}.${info.name}"
                        easyAPIConfig.add(SimpleConfigEntry(moduleSwitchPath, true))
                        if (!easyAPIConfig.getPathValue(moduleSwitchPath).toString().toBoolean()) EasyAPIModuleManager.disable(module)
                    }
                }
            })

        EasyAPI.TITLE = getTitle()

        easyAPIPlaceholder = SimplePlaceholder(
            owner = this,
            identifier = PLACEHOLDER_EASYAPI,
            onRequestFunc = { _, identifier ->
                when(identifier) {
                    "version" -> EasyAPI.VERSION
                    "git_version" -> EasyAPI.GIT_VERSION
                    "title" -> EasyAPI.TITLE
                    else -> "&cNON EXIST PLACEHOLDER"
                }
            }
        ).register()

    }

    override fun moduleDisable() {

    }

}