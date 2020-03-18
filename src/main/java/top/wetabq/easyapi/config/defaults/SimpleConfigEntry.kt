package top.wetabq.easyapi.config.defaults

class SimpleConfigEntry<T>(val path: String, defaultValue: T) {

    val value: T = defaultValue

}