package top.wetabq.easyapi.module.defaults

import cn.nukkit.Player
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerChatEvent
import cn.nukkit.event.player.PlayerDeathEvent
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerRespawnEvent
import cn.nukkit.scheduler.PluginTask
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.*
import top.wetabq.easyapi.config.defaults.SimpleConfigEntry
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule
import top.wetabq.easyapi.placeholder.PlaceholderExpansion
import top.wetabq.easyapi.placeholder.SimplePlaceholder
import top.wetabq.easyapi.placeholder.getPlaceholderExpression
import top.wetabq.easyapi.utils.color

object ChatNameTagFormatModule : SimpleEasyAPIModule() {

    const val MODULE_NAME = "ChatNameTagFormatModule"
    const val AUTHOR = "WetABQ"

    const val CHAT_CONFIG = "chatNameTagConfig"
    const val CHAT_LISTENER = "chatNameTagListener"

    const val NAME_TAG_FORMAT_PATH = "nameTagFormat"
    const val CHAT_FORMAT_PATH = "chatFormat"
    const val REFRESH_NAME_TAG_PERIOD_PATH = "refreshNameTagPeriod"

    const val PLACEHOLDER_IDENTIFIER = "chatnametag"
    const val PLACEHOLDER_NAMETAG = "nametag"
    const val PLACEHOLDER_PLAYER_NAME = "player_name"
    const val PLACEHOLDER_CHAT_MESSAGE = "%message%"
    const val PLACEHOLDER_PERMISSION_PREFIX = "permission_prefix"
    const val PLACEHOLDER_PERMISSION_SUFFIX = "permission_suffix"

    lateinit var chatConfig: SimpleConfigAPI
    lateinit var nameTagChangeTask: PluginTask<*>
    lateinit var chatNameTagPlaceholderExpansion: PlaceholderExpansion

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI.INSTANCE,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1, 0, 2)
    )

    fun getNameTagFormat(): String = chatConfig.getPathValue(NAME_TAG_FORMAT_PATH).toString()

    fun setNameTagFormat(new: String) = chatConfig.setPathValue(SimpleConfigEntry(NAME_TAG_FORMAT_PATH, new))

    fun getChatFormat(): String = chatConfig.getPathValue(CHAT_FORMAT_PATH).toString()

    fun setChatFormat(new: String) = chatConfig.setPathValue(SimpleConfigEntry(CHAT_FORMAT_PATH, new))

    fun getRefreshNameTagPeriod(): Int = (chatConfig.getPathValue(REFRESH_NAME_TAG_PERIOD_PATH).toString().toInt())

    fun String.getPlaceholderExpression(): String = getPlaceholderExpression(PLACEHOLDER_IDENTIFIER)

    override fun moduleRegister() {

        chatConfig = this.registerAPI(CHAT_CONFIG, SimpleConfigAPI(this.getModuleInfo().moduleOwner))
            .add(SimpleConfigEntry(NAME_TAG_FORMAT_PATH, "${PLACEHOLDER_PERMISSION_PREFIX.getPlaceholderExpression()} &r&e${PLACEHOLDER_PLAYER_NAME.getPlaceholderExpression()}&r ${PLACEHOLDER_PERMISSION_SUFFIX.getPlaceholderExpression()}"))
            .add(SimpleConfigEntry(CHAT_FORMAT_PATH, "${PLACEHOLDER_NAMETAG.getPlaceholderExpression()} &r&c≫&r &7$PLACEHOLDER_CHAT_MESSAGE"))
            .add(SimpleConfigEntry(REFRESH_NAME_TAG_PERIOD_PATH, 20))

        val nameTagFormat = getNameTagFormat()
        val chatFormat = getChatFormat()
        val refreshNameTagPeriod = getRefreshNameTagPeriod()

        chatNameTagPlaceholderExpansion = SimplePlaceholder(
            owner = this,
            identifier = PLACEHOLDER_IDENTIFIER,
            onRequestFunc = { player, identifier ->
                if (player is Player) {
                    when (identifier) {
                        PLACEHOLDER_NAMETAG -> PlaceholderAPI.setPlaceholder(player, nameTagFormat)
                        PLACEHOLDER_PLAYER_NAME -> player.name
                        PLACEHOLDER_PERMISSION_PREFIX -> {
                            if (PermissionGroupAPI.compatibilityCheck.isCompatible()) {
                                val fix = PermissionGroupAPI.getFix(player.name)?:""
                                if (fix == " ") "" else fix
                            } else ""
                        }
                        PLACEHOLDER_PERMISSION_SUFFIX -> {
                            if (PermissionGroupAPI.compatibilityCheck.isCompatible()) {
                                val fix = PermissionGroupAPI.getFix(player.name, false)?:""
                                if(fix == " ") "" else fix
                            } else ""
                        }
                        else -> "&cNON EXIST PLACEHOLDER"
                    }
                } else "NULL"
            },
            placeholderDescription = linkedMapOf(
                PLACEHOLDER_NAMETAG to "represents the player name tag which generate by EasyAPI",
                PLACEHOLDER_PLAYER_NAME to "represents the player name",
                PLACEHOLDER_PERMISSION_PREFIX to "represents the player permission group prefix",
                PLACEHOLDER_PERMISSION_SUFFIX to "represents the player permission group suffix"
            )
        ).register()




        /*val nameTagFormatter = object : MessageFormatter<String> {
            override fun format(message: String, data: String): String {
                //IF data IS PLAYER NAME
                var final = message
                if (EasyAPI.server.getPlayer(data) is Player) {
                    if (message.contains(EASY_NAME_TAG_PLACEHOLDER)) {
                        final = final.replace(EASY_NAME_TAG_PLACEHOLDER, nameTagFormat)
                    }
                    final = final.replace(PLAYER_NAME_PLACEHOLDER, data)
                    final = if (PermissionGroupAPI.compatibilityCheck.isCompatible()) {
                        final
                            .replace(PERMISSION_GROUP_PREFIX_PLACEHOLDER, PermissionGroupAPI.getFix(data) ?: "")
                            .replace(PERMISSION_GROUP_SUFFIX_PLACEHOLDER, PermissionGroupAPI.getFix(data, false) ?: "")
                    } else {
                        final.replace(PERMISSION_GROUP_PREFIX_PLACEHOLDER, "").replace(PERMISSION_GROUP_SUFFIX_PLACEHOLDER, "")
                    }
                }
                return final
            }
        }

        val chatFormatter = object : MessageFormatter<PlayerChatEvent> {
            override fun format(message: String, data: PlayerChatEvent): String = nameTagFormatter.format(message, data.player.name).replace(CHAT_MESSAGE_PLACEHOLDER, data.message)
        }

        MessageFormatAPI.registerFormatter(NAME_TAG_FORMATTER, String::class.java, nameTagFormatter)
        MessageFormatAPI.registerFormatter(CHAT_FORMATTER, PlayerChatEvent::class.java, chatFormatter)*/

        this.registerAPI(CHAT_LISTENER, NukkitListenerAPI(this.getModuleInfo().moduleOwner))
            .add(object: Listener {

                @EventHandler
                fun onChatEvent(event: PlayerChatEvent) {
                    event.format = PlaceholderAPI.setPlaceholder(event.player, chatFormat).replace(PLACEHOLDER_CHAT_MESSAGE, event.message).color()
                }

                @EventHandler
                fun onJoinEvent(event: PlayerJoinEvent) {
                    event.player.nameTag = PlaceholderAPI.setPlaceholder(event.player, nameTagFormat)
                }

                @EventHandler
                fun onRespawnEvent(event: PlayerRespawnEvent) {
                    event.player.nameTag = PlaceholderAPI.setPlaceholder(event.player, nameTagFormat)
                }

                @EventHandler
                fun onDeathEvent(event: PlayerDeathEvent) {
                    event.entity.nameTag = PlaceholderAPI.setPlaceholder(event.entity, nameTagFormat)
                }

            })



        nameTagChangeTask = SimplePluginTaskAPI.repeating(refreshNameTagPeriod) { _, _ ->
            getModuleInfo().moduleOwner.server.onlinePlayers.values.forEach { player ->
                if (player.isAlive) player.nameTag = PlaceholderAPI.setPlaceholder(player, nameTagFormat)
            }
        }

    }

    override fun moduleDisable() {
        nameTagChangeTask.cancel()
    }


}