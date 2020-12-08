/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.module.defaults

import cn.nukkit.Player
import cn.nukkit.scheduler.PluginTask
import cn.nukkit.utils.TextFormat
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.defaults.PluginTaskAPI
import top.wetabq.easyapi.module.ModuleInfo
import top.wetabq.easyapi.module.ModuleVersion
import top.wetabq.easyapi.module.SimpleEasyAPIModule
import top.wetabq.easyapi.screen.ScreenShow
import top.wetabq.easyapi.screen.ShowType
import top.wetabq.easyapi.task.PluginTaskEntry
import top.wetabq.easyapi.utils.color
import kotlin.math.floor

object ScreenShowModule : SimpleEasyAPIModule() {

    const val MODULE_NAME = "ScreenShow"
    const val AUTHOR = "WetABQ"

    const val LOWEST_PRIORITY = 0
    const val LOW_PRIORITY = 1
    const val MID_PRIORITY = 10
    const val HIGH_PRIORITY = 99
    const val HIGHEST_PRIORITY = 100

    private var lastTipTime = System.currentTimeMillis()
    private var lastPopupTime = System.currentTimeMillis()

    private val screenShowList = arrayListOf<ScreenShow>()
    private val alternateList = arrayListOf<ScreenShow>()

    const val SHOW_TASK = "showTask"

    override fun getModuleInfo(): ModuleInfo = ModuleInfo(
        EasyAPI.INSTANCE,
        EasyBaseModule.MODULE_NAME,
        EasyBaseModule.AUTHOR,
        ModuleVersion(1,0,0)
    )

    override fun moduleRegister() {
        this.registerAPI(SHOW_TASK, PluginTaskAPI<EasyAPI>(EasyAPI.INSTANCE))
            .add(PluginTaskEntry(
                object : PluginTask<EasyAPI>(EasyAPI.INSTANCE) {

                    override fun onRun(p0: Int) {
                        sendAll()
                    }

                },
                5
                ))
    }

    override fun moduleDisable() {
        screenShowList.clear()
        alternateList.clear()
    }

    /*
    exclusive alternative |
    alternative |
    exclusive alternative |
    alternative |
    exclusive -
    alternative |
    alternative |
    ----------
    all false +
    alternative + |
    exclusive |
    exclusive alternative
     */

    /*
    目前来说，低优先级并且没有启用 alternative 的 tip 很容易被比其高优先级的 always tip 挡住以至于一直不显示
     */
    @Suppress("UNCHECKED_CAST")
    fun sendAll() {
        val cacheTip = hashMapOf<Player, StringBuilder>()
        val cachePopup = hashMapOf<Player, StringBuilder>()

        screenShowList.sortByDescending { it.showPriority }

        val iterator = (screenShowList.clone() as ArrayList<ScreenShow>).iterator()
        while (iterator.hasNext()) { // dont change to forEach
            val screenShow = iterator.next()
            val nextLine = if (screenShow.exclusive) "" else "\n&r".color()
            if (screenShow.sendTime == -1L) {
                screenShow.sendTime = System.currentTimeMillis()
                if (screenShow.showType == ShowType.POPUP) addToCache(
                    cachePopup,
                    screenShow.targetPlayers,
                    screenShow.showMessage + nextLine
                )
                else if (screenShow.showType == ShowType.TIP) addToCache(
                    cacheTip,
                    screenShow.targetPlayers,
                    screenShow.showMessage + nextLine
                )
            } else if (screenShow.sendTime + screenShow.durationTime >= System.currentTimeMillis() || screenShow.durationTime == -1) {
                if (screenShow.showType == ShowType.POPUP) addToCache(
                    cachePopup,
                    screenShow.targetPlayers,
                    screenShow.showMessage + nextLine
                )
                else if (screenShow.showType == ShowType.TIP) addToCache(
                    cacheTip,
                    screenShow.targetPlayers,
                    screenShow.showMessage + nextLine
                )
            } else if (screenShow.sendTime + screenShow.durationTime < System.currentTimeMillis() && screenShow.durationTime != -1) {
                screenShowList.remove(screenShow)
            }

            if (screenShow.exclusive) break
        }

        cachePopup.forEach { (player, popup) ->
            player.sendPopup(popup.toString())
            lastPopupTime = System.currentTimeMillis()
        }

        cacheTip.forEach { (player, tip) ->
            player.sendTip(tip.toString())
            lastTipTime = System.currentTimeMillis()
        }

    }

    // If alternate collection full of `always` screenShow or just empty, it can start next alternating
    fun canStartNextAlternate(): Boolean {
        if (alternateList.isEmpty()) return true
        var flag = true
        alternateList.forEach {
            if (it.durationTime != -1) flag = false
        }
        return flag
    }

    fun realCanStartNextAlternate(): Boolean {
        return alternateList.isEmpty()
    }

    fun addToCache(cache: HashMap<Player, StringBuilder>, players: Collection<Player>, str: String) {
        players.forEach {
            if (!cache.containsKey(it)) cache[it] = StringBuilder()
            cache[it]?.append(str)
        }
    }

    fun addScreenShow(screenShow: ScreenShow) {
        screenShowList.add(screenShow)
    }



}