package top.wetabq.easyapi.api.default

import cn.nukkit.Server
import cn.nukkit.event.Event
import cn.nukkit.event.HandlerList
import cn.nukkit.event.Listener
import cn.nukkit.utils.Utils
import top.wetabq.easyapi.api.DynamicIntegrateAPI
import top.wetabq.easyapi.module.EasyAPIModule
import java.lang.reflect.Method
import java.util.*

class NukkitListenerAPI<M: EasyAPIModule>(private val module: M) : DynamicIntegrateAPI<Listener, M> {

    private val listeners = arrayListOf<Listener>()

    override fun add(t: Listener): M {
        listeners.add(t)
        Server.getInstance().pluginManager.registerEvents(t, module.getModuleInfo().moduleOwner)
        return module
    }

    override fun remove(t: Listener): M {
        var methods: Set<Method> = setOf()
        try {
            val publicMethods: Array<Method> = t.javaClass.methods
            val privateMethods: Array<Method> = t.javaClass.declaredMethods
            methods = HashSet(publicMethods.size + privateMethods.size, 1.0f)
            Collections.addAll(methods, *publicMethods)
            Collections.addAll(methods, *privateMethods)
        } catch (e: NoClassDefFoundError) {
           e.printStackTrace()
        }

        for (method in methods) {
            if (method.isBridge || method.isSynthetic) {
                continue
            }
            lateinit var checkClass: Class<*>
            if (method.parameterTypes.size != 1 || !Event::class.java.isAssignableFrom(method.parameterTypes[0].also { checkClass = it })) {
                continue
            }
            val eventClass = checkClass.asSubclass(Event::class.java)
            method.isAccessible = true
            var clazz: Class<*> = eventClass
            while (Event::class.java.isAssignableFrom(clazz)) {
                // This loop checks for extending deprecated events
                clazz = clazz.superclass
            }
            this.getEventListeners(eventClass).unregister(t)
        }
        return module
    }

    @Throws(IllegalAccessException::class)
    private fun getEventListeners(type: Class<out Event>): HandlerList {
        return try {
            val method = getRegistrationClass(type).getDeclaredMethod("getHandlers")
            method.isAccessible = true
            method.invoke(null) as HandlerList
        } catch (e: NullPointerException) {
            throw IllegalArgumentException("getHandlers method in " + type.name + " was not static!")
        } catch (e: Exception) {
            throw IllegalAccessException(Utils.getExceptionMessage(e))
        }
    }

    @Throws(IllegalAccessException::class)
    private fun getRegistrationClass(clazz: Class<out Event>): Class<out Event> {
        return try {
            clazz.getDeclaredMethod("getHandlers")
            clazz
        } catch (e: NoSuchMethodException) {
            if (clazz.superclass != null && clazz.superclass != Event::class.java
                && Event::class.java.isAssignableFrom(clazz.superclass)
            ) {
                getRegistrationClass(clazz.superclass.asSubclass(Event::class.java))
            } else {
                throw IllegalAccessException("Unable to find handler list for event " + clazz.name + ". Static getHandlers method required!")
            }
        }
    }

    override fun removeAll() {
        listeners.forEach { t -> remove(t) }
    }

    override fun getAll() : Collection<Listener> = listeners

}