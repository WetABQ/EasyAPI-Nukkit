/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.module

import top.wetabq.easyapi.module.exception.ModuleStatusException

abstract class SimpleEasyAPIModule: EasyAPIModule() {

    protected var status = ModuleStatus.UNLOAD


    override fun getModuleStatus(): ModuleStatus = status

    override fun register() {
        when (this.status) {
            ModuleStatus.DISABLED, ModuleStatus.UNLOAD -> {
                this.status = ModuleStatus.ENABLED
            }
            ModuleStatus.ENABLED -> throw ModuleStatusException(this, "module has registered")
        }
        moduleRegister()
    }

    abstract fun moduleRegister()

    abstract fun moduleDisable()

    override fun disable() {
        this.status = ModuleStatus.DISABLED
        moduleDisable()
    }


}