package top.wetabq.easyapi.module.defaults

import cn.nukkit.Player
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParameter
import cn.nukkit.utils.TextFormat
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.CommandAPI
import top.wetabq.easyapi.command.EasyCommand
import top.wetabq.easyapi.command.EasySubCommand
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule
import top.wetabq.easyapi.placeholder.PlaceholderException
import top.wetabq.easyapi.placeholder.PlaceholderExpansion
import top.wetabq.easyapi.placeholder.SimplePlaceholder
import top.wetabq.easyapi.utils.color
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern

object PlaceholderManager : SimpleEasyAPIModule() {

    const val MODULE_NAME = "PlaceholderManager"
    const val AUTHOR = "WetABQ"

    const val PLACEHOLDER_COMMAND = "placeholderCommand"
    const val PLAYER_PLACEHOLDER_IDENTIFIER = "player"
    const val MATH_PLACEHOLDER_IDENTIFIER = "math"

    lateinit var playerPlaceholderExpansion: PlaceholderExpansion

    private val placeholderExpansions = ConcurrentHashMap<String, PlaceholderExpansion>()
    private val expansionPattern = ConcurrentHashMap<String, Pattern>()

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI.INSTANCE,
        EasyBaseModule.MODULE_NAME,
        EasyBaseModule.AUTHOR,
        ModuleVersion(1,0,0)
    )

    override fun moduleRegister() {

        playerPlaceholderExpansion = SimplePlaceholder(
            owner = this,
            identifier = PLAYER_PLACEHOLDER_IDENTIFIER,
            onRequestFunc = { player, identifier ->
                if (player is Player) {
                    when(identifier) {
                        "ping" -> player.ping.toString()
                        "colored_ping" -> "&${if (player.ping > 100) "c" else if (player.ping > 50) "e" else "a"}${player.ping}"
                        "name" -> player.name

                        else -> "&cNON EXIST PLACEHOLDER"
                    }
                } else {
                    ""
                }
            }
        )

        this.registerAPI(PLACEHOLDER_COMMAND, CommandAPI())
            .add(object : EasyCommand("placeholder") {

                init {
                    this.aliases = arrayOf("ph")
                    subCommand.add(object : EasySubCommand("list") {
                        override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
                            val s = buildString {
                                append("${TextFormat.GOLD}------Placeholders------\n")
                                placeholderExpansions.values.forEach {
                                    it.getPlaceholderDescription().forEach { (valueIdentifier, description) ->
                                        // e.g: - %easyapi_version% -- represents the EasyAPI plugin version
                                        append("${TextFormat.AQUA}- ${TextFormat.BOLD}${TextFormat.RED}%${it.getIdentifier()}_${valueIdentifier}% ${TextFormat.DARK_GRAY}-- ${TextFormat.GRAY}$description")
                                    }
                                }
                            }
                            return true
                        }

                        override fun getAliases(): Array<String>? = arrayOf("l")

                        override fun getDescription(): String = "List all placeholder that has been registered into system."

                        override fun getParameters(): Array<CommandParameter>? = null
                    })

                    loadCommandBase()

                }
            })
    }

    override fun moduleDisable() {
        unregisterAllPlaceholderExpansion()
    }

    fun applyPlaceholder(player: Player?, content: String, colorize: Boolean = true): String {
        var final = content
        expansionPattern.forEach { (identifier, pattern) ->
            var m = pattern.matcher(final)
            while (m.find()) {
                final = content.replace(m.group(0), placeholderExpansions[identifier]?.onRequest(player, m.group(1))?:"${TextFormat.RED}ERROR_PLACEHOLDER")
                m = pattern.matcher(final)
            }
        }
        return if (colorize) final.color() else final
    }

    fun getExpansion(identifier: String): PlaceholderExpansion? = placeholderExpansions[identifier]

    fun registerPlaceholderExpansion(expansion: PlaceholderExpansion) {
        if (expansion.canRegister()) {
            if (placeholderExpansions.contains(expansion.getIdentifier())) getModuleInfo().moduleOwner.logger.warning("Repeated placeholder registration detected.")
            placeholderExpansions[expansion.getIdentifier()] = expansion
            expansionPattern[expansion.getIdentifier()] = Pattern.compile("%${expansion.getIdentifier()}_([^%]+)%")
        } else throw PlaceholderException(expansion.getOwner(), "can't register the corresponding placeholder, the necessary plugins or dependencies may be missing.")
    }

    fun unregisterPlaceholderExpansion(identifier: String) {
        if (placeholderExpansions.contains(identifier)) {
            placeholderExpansions[identifier]?.onUnregister()
            placeholderExpansions.remove(identifier)
        }
    }

    fun unregisterAllPlaceholderExpansion() {
        placeholderExpansions.keys.forEach { unregisterPlaceholderExpansion(it) }
    }

}