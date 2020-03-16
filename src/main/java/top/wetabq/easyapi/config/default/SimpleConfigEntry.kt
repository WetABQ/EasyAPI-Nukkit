package top.wetabq.easyapi.config.default

class SimpleConfigEntry<T>(val path: String, defaultValue: T) {

    val value: T = defaultValue

}