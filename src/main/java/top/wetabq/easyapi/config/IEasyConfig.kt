package top.wetabq.easyapi.config

interface IEasyConfig {
    
    fun init()

    fun spawnDefaultConfig()

    fun save()

    fun reload()

    fun isEmpty(): Boolean

}