package top.wetabq.easyapi.api

import top.wetabq.easyapi.module.ModuleRegistry


interface DynamicIntegrateAPI<T> {

    fun add(t: T): ModuleRegistry

    fun remove(t: T): ModuleRegistry

    fun getAll(): Collection<T>

    fun removeAll()

}