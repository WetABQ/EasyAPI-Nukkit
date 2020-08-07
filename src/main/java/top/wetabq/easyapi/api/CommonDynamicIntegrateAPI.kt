/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api


abstract class CommonDynamicIntegrateAPI<T, out I: DynamicIntegrateAPI<*, I>> : DynamicIntegrateAPI<T, I> {

    protected val interfaceList = arrayListOf<T>()

    override fun add(t: T): I {
        interfaceList.add(t)
        return addInterface(t)
    }

    override fun remove(t: T): I {
        interfaceList.remove(t)
        return removeInterface(t)
    }

    protected abstract fun addInterface(t: T): I

    protected abstract fun removeInterface(t: T): I

    override fun getAll(): Collection<T> = interfaceList

    @Suppress("UNCHECKED_CAST")
    override fun removeAll() {
        (interfaceList.clone() as ArrayList<T> ).forEach { remove(it) }
    }

}