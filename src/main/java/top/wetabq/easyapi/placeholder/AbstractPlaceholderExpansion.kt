package top.wetabq.easyapi.placeholder

import top.wetabq.easyapi.module.defaults.PlaceholderManager

abstract class AbstractPlaceholderExpansion : PlaceholderExpansion {

    fun isRegistered(): Boolean = PlaceholderManager.getExpansion(this.getIdentifier()) != null

    fun register(): PlaceholderExpansion {
        PlaceholderManager.registerPlaceholderExpansion(this)
        return this
    }

    fun unregister(): PlaceholderExpansion {
        PlaceholderManager.unregisterPlaceholderExpansion(this.getIdentifier())
        return this
    }

    override fun onUnregister() {

    }

    override fun getValueIdentifierList(): Collection<String> = getPlaceholderDescription().keys

    fun getPlaceholderExpression(valueIdentifier: String) = "%${getIdentifier()}_$valueIdentifier%"

}