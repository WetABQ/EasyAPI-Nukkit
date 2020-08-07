/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.api.defaults

import cn.nukkit.Player
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.cacheddata.CachedPermissionData
import net.luckperms.api.context.ContextManager
import net.luckperms.api.query.QueryOptions
import ru.nukkit.multipass.Multipass
import top.wetabq.easyapi.api.CompatibilityCheck
import top.wetabq.easyapi.api.SimpleIntegrateAPI


object PermissionGroupAPI : SimpleIntegrateAPI {

    const val LUCK_PERMS = "LuckPerms"
    const val MULTI_PASS = "Multipass"

    val compatibilityCheck = CompatibilityCheck(listOf(
        LUCK_PERMS,
        MULTI_PASS
    ))


    fun addPlayerToGroup(player: String, groupName: String) {
        compatibilityCheck.doCompatibilityAction(mapOf(
            LUCK_PERMS to {
                LuckPermsProvider.get().userManager.getUser(player)?.primaryGroup = groupName
            },
            MULTI_PASS to {
                Multipass.addGroup(player, groupName)
            }
        ))
    }

    fun isPlayerInGroup(player: Player, group: String): Boolean? {
        return compatibilityCheck.doCompatibilityAction(mapOf(
            LUCK_PERMS to {
                player.hasPermission("group.$group")
            },
            MULTI_PASS to {
                Multipass.isInGroup(player, group)
            }
        ))
    }

    fun getPlayerGroup(player: Player, possibleGroups: Collection<String>): String? {
        return compatibilityCheck.doCompatibilityAction(mapOf(
            LUCK_PERMS to {
                var final = ""
                for (group in possibleGroups) {
                    if (player.hasPermission("group.$group")) {
                        final = group
                    }
                }
                final
            },
            MULTI_PASS to {
                Multipass.getGroup(player)
            }
        ))
    }

    fun hasPermission(player: Player, permission: String): Boolean? {
        return compatibilityCheck.doCompatibilityAction(mapOf(
            LUCK_PERMS to {
                LuckPermsProvider.get().userManager.getUser(player.name)?.let { user ->
                    val contextManager: ContextManager = LuckPermsProvider.get().contextManager
                    val contextSet = contextManager.getContext(user).orElseGet(contextManager::getStaticContext)
                    val permissionData: CachedPermissionData = user.cachedData.getPermissionData(QueryOptions.contextual(contextSet))
                    permissionData.checkPermission(permission).asBoolean()
                }
            },
            MULTI_PASS to {
                Multipass.hasPermission(player.level.folderName, player.name, permission)
            }
        ))
    }

    fun getFix(player: String, isPrefix: Boolean = true): String? {
        return compatibilityCheck.doCompatibilityAction(mapOf(
            LUCK_PERMS to {
                LuckPermsProvider.get().userManager.getUser(player)?.let { user ->
                    val contextManager: ContextManager = LuckPermsProvider.get().contextManager
                    val contextSet = contextManager.getContext(user).orElseGet(contextManager::getStaticContext)
                    val metaData = user.cachedData.getMetaData(QueryOptions.contextual(contextSet))
                    return@let if(isPrefix) metaData.prefix else metaData.suffix
                }
            },
            MULTI_PASS to {
                if (isPrefix) Multipass.getPrefix(player) else Multipass.getSuffix(player)
            }
        ))
    }

}