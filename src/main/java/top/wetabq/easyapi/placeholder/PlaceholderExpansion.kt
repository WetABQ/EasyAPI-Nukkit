package top.wetabq.easyapi.placeholder

import cn.nukkit.Player
import top.wetabq.easyapi.module.IEasyAPIModule

fun String.getPlaceholderExpression(identifier: String): String = "%${identifier}_$this%"

interface PlaceholderExpansion {

    /**
     * If you do not have any dependency, just return true
     * Instead, if you have some dependency, you need to determine whether the dependency exists
     *
     * @return can be registered by PlaceholderManager?
     * @see top.wetabq.easyapi.module.defaults.PlaceholderManager
     */
    fun canRegister(): Boolean


    /**
     * Each placeholder expansion should have a module owner
     *
     * @return Placeholder expansion owner
     */
    fun getOwner(): IEasyAPIModule


    /**
     * Placeholder identifier is a part of placeholder in order to classify a category of the plugin's placeholder.
     * e.g: %identifier_value%
     *      %easyapi_version% - In that case, 'easyapi' is a identifier
     *
     * @return Placeholder identifier
     */
    fun getIdentifier(): String

    /**
     * @return a list of value identifier
     */
    fun getValueIdentifierList(): Collection<String>

    /**
     * That can be showed for some case that need help content or to list placeholder.
     *
     * @return a map contains (value identifier -> description).
     */
    fun getPlaceholderDescription(): LinkedHashMap<String, String>

    /**
     * This is the method called when a placeholder with your identifier is found and needs a value to replace it.
     *
     * @param player can be a null value
     * @param valueIdentifier A string containing the value identifier
     *      e.g: %identifier_valueIdentifier%
     *      %easyapi_version% - In that case, 'version' is a value identifier
     *
     * @return the placeholder value should be returned, and it can be null.
     */
    fun onRequest(player: Player?, valueIdentifier: String): String?

    /**
     * This is the method called when the placeholder is required unregister.
     */
    fun onUnregister()

    /**
     * get placeholder expression as normal string
     */
    fun getPlaceholderExpression(valueIdentifier: String): String

}