package top.wetabq.easyapi.module.default

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerChatEvent
import cn.nukkit.event.player.PlayerDeathEvent
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerRespawnEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.default.*
import top.wetabq.easyapi.config.default.SimpleConfigEntry
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule

object ChatNameTagFormatModule : SimpleEasyAPIModule() {

    const val MODULE_NAME = "ChatNameTagFormatModule"
    const val AUTHOR = "WetABQ"

    const val CHAT_CONFIG = "chatNameTagConfig"
    const val CHAT_LISTENER = "chatNameTagListener"

    const val NAME_TAG_FORMAT_PATH = "nameTagFormat"
    const val CHAT_FORMAT_PATH = "chatFormat"

    const val EASY_NAME_TAG_PLACEHOLDER = "%easy_nametag%"
    const val PLAYER_NAME_PLACEHOLDER = "%player_name%"
    const val CHAT_MESSAGE_PLACEHOLDER = "%chat_message%"
    const val PERMISSION_GROUP_PREFIX_PLACEHOLDER = "%permission_group_prefix%"
    const val PERMISSION_GROUP_SUFFIX_PLACEHOLDER = "%permission_group_suffix%"

    const val NAME_TAG_FORMATTER = "nameTagFormatter"
    const val CHAT_FORMATTER = "chatFormatter"

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI,
        MODULE_NAME,
        AUTHOR,
        ModuleVersion(1, 0, 0)
    )

    override fun moduleRegister() {
        val chatConfig = this.registerAPI(CHAT_CONFIG, SimpleConfigAPI(this.getModuleInfo().moduleOwner))
            .add(SimpleConfigEntry(NAME_TAG_FORMAT_PATH, "$PERMISSION_GROUP_PREFIX_PLACEHOLDER &r&e$PLAYER_NAME_PLACEHOLDER&r $PERMISSION_GROUP_SUFFIX_PLACEHOLDER"))
            .add(SimpleConfigEntry(CHAT_FORMAT_PATH, "$EASY_NAME_TAG_PLACEHOLDER &r&c≫&r &7$CHAT_MESSAGE_PLACEHOLDER"))

        val nameTagFormat = chatConfig.getPath(NAME_TAG_FORMAT_PATH)
        val chatFormat = chatConfig.getPath(CHAT_FORMAT_PATH)

        val nameTagFormatter = object : MessageFormatter<String> {
            override fun format(message: String, data: String): String {
                //IF data IS PLAYER NAME
                var final = message
                if (Server.getInstance().getPlayer(data) is Player) {
                    if (data.contains(EASY_NAME_TAG_PLACEHOLDER)) {
                        final = final.replace(EASY_NAME_TAG_PLACEHOLDER, nameTagFormat)
                    }
                    final = final.replace(PLAYER_NAME_PLACEHOLDER, data)
                    if (PermissionGroupAPI.compatibilityCheck.isCompatible()) {
                        final = final
                            .replace(PERMISSION_GROUP_PREFIX_PLACEHOLDER, PermissionGroupAPI.getFix(data) ?: "")
                            .replace(PERMISSION_GROUP_SUFFIX_PLACEHOLDER, PermissionGroupAPI.getFix(data, false) ?: "")
                    }
                }
                return final
            }
        }

        val chatFormatter = object : MessageFormatter<PlayerChatEvent> {
            override fun format(message: String, data: PlayerChatEvent): String = nameTagFormatter.format(message, data.player.name).replace(CHAT_MESSAGE_PLACEHOLDER, data.message)
        }

        MessageFormatAPI.registerFormatter(NAME_TAG_FORMATTER, String::class.java, nameTagFormatter)
        MessageFormatAPI.registerFormatter(CHAT_FORMATTER, PlayerChatEvent::class.java, chatFormatter)

        this.registerAPI(CHAT_LISTENER, NukkitListenerAPI(this.getModuleInfo().moduleOwner))
            .add(object: Listener {

                @EventHandler
                fun onChatEvent(event: PlayerChatEvent) {
                    GlobalScope.launch {
                        coroutineCallEvent(event) {
                            event.message = MessageFormatAPI.format(chatFormat, event)
                        }
                    }
                }

                @EventHandler
                fun onJoinEvent(event: PlayerJoinEvent) {
                    event.player.nameTag = MessageFormatAPI.format(nameTagFormat, event.player.name)
                }

                @EventHandler
                fun onRespawnEvent(event: PlayerRespawnEvent) {
                    event.player.nameTag = MessageFormatAPI.format(nameTagFormat, event.player.name)
                }

                @EventHandler
                fun onDeathEvent(event: PlayerDeathEvent) {
                    event.entity.nameTag = MessageFormatAPI.format(nameTagFormat, event.entity.name)
                }

            })
    }

    override fun moduleDisable() {

    }


}