package top.wetabq.easyapi.api

interface DynamicIntegrateAPI<T, out I: DynamicIntegrateAPI<*, I>> {

    fun add(t: T): I

    fun remove(t: T): I

    fun getAll(): Collection<T>

    fun removeAll()

}