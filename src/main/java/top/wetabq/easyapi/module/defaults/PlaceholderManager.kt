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
import top.wetabq.easyapi.utils.getCardinalDirection
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern
import kotlin.math.roundToInt


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
                        "uuid" -> player.uniqueId.toString()
                        "has_played_before" -> player.hasPlayedBefore().customToString()
                        "online" -> player.isOnline.customToString()
                        "is_whitelisted" -> player.isWhitelisted.customToString()
                        "is_banned" -> player.isBanned.customToString()
                        "is_op" -> player.isOp.customToString()
                        "first_played" -> player.firstPlayed.toString()
                        "last_played" -> player.lastPlayed.toString()
                        "spawn_x" -> player.spawn.x.toInt().toString()
                        "spawn_y" -> player.spawn.y.toInt().toString()
                        "spawn_z" -> player.spawn.z.toInt().toString()
                        "world_spawn" -> player.level.spawnLocation.toString()
                        "servername" -> player.level.server.name
                        "displayname" -> player.displayName
                        "gamemode" -> player.gamemode.toString()
                        "direction" -> player.getCardinalDirection()
                        "world" -> player.level.name
                        "x" -> player.x.toInt().toString()
                        "y" -> player.y.toInt().toString()
                        "z" -> player.z.toInt().toString()
                        "ip" -> player.address
                        "allow_flight" -> player.allowFlight.customToString()
                        "exp" -> player.experience.toString()
                        "exp_level" -> player.experienceLevel.toString()
                        "food_level" -> player.foodData.foodSaturationLevel.toInt().toString()
                        "health" -> player.health.toString()
                        "health_rounded" -> player.health.roundToInt().toString()
                        "max_health" -> player.maxHealth.toString()
                        "item_in_hand" -> player.inventory.itemInHand.name
                        "ticks_lived" -> player.ticksLived.toString()
                        "seconds_lived" -> player.ticksLived.div(20).toString()
                        "minutes_lived" -> player.ticksLived.div(20*60).toString()
                        "speed" -> player.movementSpeed.toInt().toString()
                        "world_time" -> player.level.time.toString()

                        else -> "&cNON EXIST PLACEHOLDER"
                    }
                } else {
                    ""
                }
            },
            placeholderDescription = linkedMapOf(
                "ping" to "ping",
                "colored_ping" to "colored ping",
                "name" to "name",
                "uuid" to "uuid",
                "has_played_before" to "has played before",
                "online" to "online",
                "is_whitelisted" to "is whitelisted",
                "is_banned" to "is banned",
                "is_op" to "is op",
                "first_played" to "first played",
                "last_played" to "last played",
                "spawn_x" to "spawn x",
                "spawn_y" to "spawn y",
                "spawn_z" to "spawn z",
                "world_spawn" to "world spawn",
                "servername" to "servername",
                "displayname" to "displayname",
                "gamemode" to "gamemode",
                "direction" to "direction",
                "world" to "world",
                "x" to "x",
                "y" to "y",
                "z" to "z",
                "ip" to "ip",
                "allow_flight" to "allow flight",
                "exp" to "exp",
                "exp_level" to "exp level",
                "food_level" to "food level",
                "health" to "health",
                "health_rounded" to "health rounded",
                "max_health" to "max health",
                "item_in_hand" to "item in hand",
                "ticks_lived" to "ticks lived",
                "seconds_lived" to "seconds lived",
                "minutes_lived" to "minutes lived",
                "speed" to "speed",
                "world_time" to "world time"
            )
        ).register()

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
                                        append("${TextFormat.AQUA}- ${TextFormat.BOLD}${TextFormat.RED}${it.getPlaceholderExpression(valueIdentifier)} ${TextFormat.DARK_GRAY}-- ${TextFormat.GRAY}$description\n")
                                    }
                                }
                            }
                            sender.sendMessage(s)
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
                final = final.replace(m.group(0), placeholderExpansions[identifier]?.onRequest(player, m.group(1))?:"${TextFormat.RED}ERROR_PLACEHOLDER")
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

    private fun Boolean.customToString() : String = if(this) "yes" else "no"

}