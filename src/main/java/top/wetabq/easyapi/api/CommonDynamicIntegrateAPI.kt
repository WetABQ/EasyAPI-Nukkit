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