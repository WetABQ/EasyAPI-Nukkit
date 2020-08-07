/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.module.defaults

import cn.nukkit.AdventureSettings
import cn.nukkit.Player
import cn.nukkit.command.CommandSender
import cn.nukkit.command.data.CommandParameter
import cn.nukkit.utils.TextFormat
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.CommandAPI
import top.wetabq.easyapi.api.defaults.EconomyAPI
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
import top.wetabq.easyapi.utils.getHealthPopupString
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern
import kotlin.math.roundToInt


object PlaceholderManager : SimpleEasyAPIModule() {

    const val MODULE_NAME = "PlaceholderManager"
    const val AUTHOR = "WetABQ"

    const val PLACEHOLDER_COMMAND = "placeholderCommand"
    const val PLAYER_PLACEHOLDER_IDENTIFIER = "player"
    const val SERVER_PLACEHOLDER_IDENTIFIER = "server"
    const val RUNTIME_PLACEHOLDER_IDENTIFIER = "runtime"
    const val DATE_PLACEHOLDER_IDENTIFIER = "date"
    const val MATH_PLACEHOLDER_IDENTIFIER = "math"

    lateinit var playerPlaceholderExpansion: PlaceholderExpansion
    lateinit var serverPlaceholderExpansion: PlaceholderExpansion
    lateinit var runtimePlaceholderExpansion: PlaceholderExpansion
    lateinit var datePlaceholderExpansion: PlaceholderExpansion

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
                        "display_name" -> player.displayName
                        "nametag" -> player.nameTag
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
                        "gamemode" -> player.gamemode.toString()
                        "direction" -> player.getCardinalDirection()
                        "world" -> player.level.name
                        "x" -> player.x.toInt().toString()
                        "y" -> player.y.toInt().toString()
                        "z" -> player.z.toInt().toString()
                        "ip" -> player.address
                        "allow_flight" -> player.adventureSettings.get(AdventureSettings.Type.ALLOW_FLIGHT).toString().toBoolean().customToString()
                        "exp" -> player.experience.toString()
                        "exp_level" -> player.experienceLevel.toString()
                        "food_level" -> player.foodData.foodSaturationLevel.toInt().toString()
                        "health" -> player.health.toString()
                        "health_rounded" -> player.health.roundToInt().toString()
                        "max_health" -> player.maxHealth.toString()
                        "item_in_hand_name" -> player.inventory.itemInHand.name
                        "item_in_hand_id" -> player.inventory.itemInHand.id.toString()
                        "item_in_hand_meta" -> player.inventory.itemInHand.damage.toString()
                        "ticks_lived" -> player.ticksLived.toString()
                        "seconds_lived" -> player.ticksLived.div(20).toString()
                        "minutes_lived" -> player.ticksLived.div(20*60).toString()
                        "speed" -> player.movementSpeed.toInt().toString()
                        "world_time" -> player.level.time.toString()
                        "health_popup" -> player.getHealthPopupString()
                        "device" -> player.loginChainData.deviceModel
                        "exp_to_next" -> Player.calculateRequireExperience(player.experienceLevel).toString()
                        "money" -> if (EconomyAPI.compatibilityCheck.isCompatible()) EconomyAPI.getMoney(player).toString() else "&cERROR"
                        else -> "&cNON EXIST PLACEHOLDER"
                    }
                } else {
                    ""
                }
            },
            placeholderDescription = linkedMapOf(
                "ping" to "represents the player's ping",
                "colored_ping" to "represents the player's colored ping",
                "name" to "represents the player's name",
                "display_name" to "represents the player's display name",
                "nametag" to "represents the player's nametag",
                "uuid" to "represents the player's uuid",
                "has_played_before" to "represents the player whether has been played before",
                "online" to "represents whether the player is online",
                "is_whitelisted" to "represents whether the player is in whitelist",
                "is_banned" to "represents whether the player is banned",
                "is_op" to "represents whether the player is op",
                "first_played" to "represents the player's first playing time",
                "last_played" to "represents the player's last playing time",
                "spawn_x" to "represents the player's spawn x",
                "spawn_y" to "represents the player's spawn y",
                "spawn_z" to "represents the player's spawn z",
                "world_spawn" to "represents the player's spawn world",
                "servername" to "represents the player's level server name",
                "gamemode" to "represents the player's gamemode",
                "direction" to "represents the player's direction",
                "world" to "represents the player's world",
                "x" to "represents the player's x",
                "y" to "represents the player's y",
                "z" to "represents the player's z",
                "ip" to "represents the player's ip",
                "allow_flight" to "represents whether the player is allowed flight",
                "exp" to "represents the player's exp",
                "exp_level" to "represents the player's exp level",
                "food_level" to "represents the player's food level",
                "health" to "represents the player's health",
                "health_rounded" to "represents the player's rounded health",
                "max_health" to "represents the player's max health",
                "item_in_hand_name" to "represents the player's item in hand",
                "ticks_lived" to "represents the player's ticks lived",
                "seconds_lived" to "represents the player's seconds lived",
                "minutes_lived" to "represents the player's minutes lived",
                "speed" to "represents the player's speed",
                "world_time" to "represents the player's world time",
                "health_popup" to "represents the player's health as a colored visual string"
            )
        ).register()


        serverPlaceholderExpansion = SimplePlaceholder(
            owner = this,
            identifier = SERVER_PLACEHOLDER_IDENTIFIER,
            onRequestFunc = { _, identifier ->
                val server = getModuleInfo().moduleOwner.server

                when (identifier) {
                    "online_player" -> server.onlinePlayers.size.toString()
                    "max_online" -> server.maxPlayers.toString()
                    "motd" -> server.motd
                    "sub_motd" -> server.subMotd
                    "tps" -> server.ticksPerSecond.toString()
                    "tps_avg" -> server.ticksPerSecondAverage.toString()
                    else -> "&cNON EXIST PLACEHOLDER"
                }
            },
            placeholderDescription = linkedMapOf(
                "online_player" to "represents the server online player",
                "max_online" to "represents the server max online player",
                "motd" to "represents the server motd",
                "sub_motd" to "represents the server sub motd",
                "tps" to "represents the server tps",
                "tps_avg" to "represents the server average tps"
            )
        ).register()

        runtimePlaceholderExpansion = SimplePlaceholder(
            owner = this,
            identifier = RUNTIME_PLACEHOLDER_IDENTIFIER,
            onRequestFunc = { _, identifier ->
                val runtime = Runtime.getRuntime()

                when (identifier) {
                    "ram_used" -> (runtime.maxMemory() - runtime.freeMemory()).toString()
                    "ram_free" -> runtime.freeMemory().toString()
                    "ram_total" -> runtime.totalMemory().toString()
                    "cores" -> runtime.availableProcessors().toString()
                    else -> "&cNON EXIST PLACEHOLDER"
                }
            },
            placeholderDescription = linkedMapOf(
                "ram_used" to "represents the server's memory used",
                "ram_free" to "represents the server's ram free",
                "ram_total" to "represents the server's ram total",
                "cores" to "represents the server's processor cores"
            )
        ).register()

        datePlaceholderExpansion = SimplePlaceholder(
            owner = this,
            identifier = DATE_PLACEHOLDER_IDENTIFIER,
            onRequestFunc = { _, identifier ->
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR).toString()
                val month = calendar.get(Calendar.MONTH).plus(1).toString()
                val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
                val date = "${year}/${month}/${day}"
                val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
                val minute = calendar.get(Calendar.MINUTE).toString()
                val second = calendar.get(Calendar.SECOND).toString()
                val time = "${hour}:${minute}:${second}"

                when (identifier) {
                    "date" -> date
                    "year" -> year
                    "month" -> month
                    "day" -> day
                    "time" -> time
                    "hour" -> hour
                    "minute" -> minute
                    "second" -> second
                    else -> "&cNON EXIST PLACEHOLDER"
                }
            },
            placeholderDescription = linkedMapOf(
                "date" to "date",
                "year" to "year",
                "month" to "month",
                "day" to "day",
                "time" to "time",
                "hour" to "hour",
                "minute" to "minute",
                "second" to "second"
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