package top.wetabq.easyapi.api

import top.wetabq.easyapi.EasyAPI

class CompatibilityCheck(private val providers: List<String>) {

    private var finalKey = "-1"

    fun check() {
        // TODO: Check server started
        providers.forEach { plugin ->
            if (EasyAPI.server.pluginManager.getPlugin(plugin) != null) {
                finalKey = plugin
            }
        }
        if (finalKey == "-1") finalKey = ""
    }


    fun <T> doCompatibilityAction(compatibleAction: Map<String, () -> T>): T? {
        var finalValue: T? = null
        if (finalKey == "-1") check()
        if (finalKey != "") {
            compatibleAction.forEach { (key, action) ->
                if (key == finalKey)  {
                    finalValue = action()
                }
            }
        } else throw CompatibilityException("don't find any compatible provider to do action")
        return finalValue
    }

    fun isCompatible(): Boolean {
        if (finalKey == "-1") check()
        return (finalKey != "")
    }

}