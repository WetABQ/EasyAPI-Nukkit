package top.wetabq.easyapi.placeholder

import top.wetabq.easyapi.module.IEasyAPIModule
import top.wetabq.easyapi.module.exception.ModuleException

class PlaceholderException(module: IEasyAPIModule, reason: String) : ModuleException(module, reason)