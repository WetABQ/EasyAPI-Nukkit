package top.wetabq.easyapi.module.default

import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule

object ScreenShowModule : SimpleEasyAPIModule() {

    const val MODULE_NAME = "ScreenShow"
    const val AUTHOR = "WetABQ"

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI,
        EasyBaseModule.MODULE_NAME,
        EasyBaseModule.AUTHOR,
        ModuleVersion(1,0,0)
    )

    override fun moduleRegister() {

    }

    override fun moduleDisable() {

    }


}