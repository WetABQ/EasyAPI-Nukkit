package top.wetabq.easyapi.module

import cn.nukkit.plugin.Plugin

data class ModuleInfo (
    val moduleOwner: Plugin,
    val name: String,
    val author: String,
    val moduleVersion: ModuleVersion
)