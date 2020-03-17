package top.wetabq.easyapi.module.exception

import top.wetabq.easyapi.module.IEasyAPIModule
import java.lang.RuntimeException

abstract class ModuleException(val module: IEasyAPIModule, reason: String) : RuntimeException(reason)

class ModuleNotRegisterException(module: IEasyAPIModule, reason: String) : ModuleException(module,reason)

class ModuleStatusException(module: IEasyAPIModule, reason: String): ModuleException(module, reason)