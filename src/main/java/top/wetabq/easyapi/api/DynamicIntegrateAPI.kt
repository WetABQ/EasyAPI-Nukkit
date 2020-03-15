package top.wetabq.easyapi.api

import top.wetabq.easyapi.module.EasyAPIModule

interface DynamicIntegrateAPI<T, M: EasyAPIModule> {

    fun add(t: T): M

    fun remove(t: T): M

    fun getAll(): Collection<T>

    fun removeAll()

}