package top.wetabq.easyapi.placeholder

import top.wetabq.easyapi.module.defaults.PlaceholderManager

abstract class AbstractPlaceholderExpansion : PlaceholderExpansion {

    fun isRegistered(): Boolean = PlaceholderManager.getExpansion(this.getIdentifier()) != null

    fun register() = PlaceholderManager.registerPlaceholderExpansion(this)

    fun unregister() = PlaceholderManager.unregisterPlaceholderExpansion(this.getIdentifier())

    override fun onUnregister() {

    }

    override fun getValueIdentifierList(): Collection<String> = getPlaceholderDescription().keys

}