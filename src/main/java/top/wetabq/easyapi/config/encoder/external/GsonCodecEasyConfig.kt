package top.wetabq.easyapi.config.encoder.external

import cn.nukkit.plugin.Plugin
import top.wetabq.easyapi.config.encoder.external.serializer.Serializer

class GsonCodecEasyConfig<T>(
    configName: String,
    plugin: Plugin,
    clazzT: Class<T>,
    defaultValue: T
) : AbstractExternalSerializerEasyConfig<T>(configName, plugin, Serializer.Gson, clazzT, defaultValue)