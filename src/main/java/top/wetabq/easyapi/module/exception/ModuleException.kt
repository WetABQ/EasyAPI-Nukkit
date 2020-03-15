package top.wetabq.easyapi.module.exception

import top.wetabq.easyapi.module.EasyAPIModule
import java.lang.RuntimeException

abstract class ModuleException(val module: EasyAPIModule,reason: String) : RuntimeException(reason)

class ModuleNotRegisterException(module: EasyAPIModule, reason: String) : ModuleException(module,reason)

class ModuleStatusException(module: EasyAPIModule, reason: String): ModuleException(module, reason)