/*
 * Copyright (c) 2020 WetABQ and contributors
 *
 *  此源代码的使用受 GNU GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU GPLv3 license that can be found through the following link.
 *
 * https://github.com/WetABQ/EasyAPI-Nukkit/blob/master/LICENSE
 */

package top.wetabq.easyapi.listener

import cn.nukkit.event.block.*
import cn.nukkit.event.entity.*
import cn.nukkit.event.inventory.CraftItemEvent
import cn.nukkit.event.inventory.InventoryPickupItemEvent
import cn.nukkit.event.inventory.InventoryTransactionEvent
import cn.nukkit.event.level.WeatherChangeEvent
import cn.nukkit.event.player.*
import cn.nukkit.event.potion.PotionApplyEvent
import cn.nukkit.event.vehicle.VehicleDestroyEvent

interface AsyncListener {

    fun onPlayerChatEvent(event: PlayerChatEvent) {}

    fun onPlayerCommandPreprocessEvent(event: PlayerCommandPreprocessEvent) {}

    fun onPlayerDeathEvent(event: PlayerDeathEvent) {}

    fun onPlayerBucketEmptyEvent(event: PlayerBucketEmptyEvent) {}

    fun onPlayerBucketFillEvent(event: PlayerBucketFillEvent) {}

    fun onPlayerDropItemEvent(event: PlayerDropItemEvent) {}

    fun onPlayerEatFoodEvent(event: PlayerEatFoodEvent) {}

    fun onPlayerFoodLevelChangeEvent(event: PlayerFoodLevelChangeEvent) {}

    fun onPlayerFormRespondedEvent(event: PlayerFormRespondedEvent) {}

    fun onPlayerGameModeChangeEvent(event: PlayerGameModeChangeEvent) {}

    fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {}

    fun onPlayerInteractEvent(event: PlayerInteractEvent) {}

    fun onPlayerItemHeldEvent(event: PlayerItemHeldEvent) {}

    fun onPlayerJoinEvent(event: PlayerJoinEvent) {}

    fun onPlayerLoginEvent(event: PlayerLoginEvent) {}

    fun onPlayerMoveEvent(event: PlayerMoveEvent) {}

    fun onPlayerPreLoginEvent(event: PlayerPreLoginEvent) {}

    fun onPlayerQuitEvent(event: PlayerQuitEvent) {}

    fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {}

    fun onPlayerTeleportEvent(event: PlayerTeleportEvent) {}

    fun onBlockBreakEvent(event: BlockBreakEvent) {}

    fun onBlockPlaceEvent(event: BlockPlaceEvent) {}

    fun onEntityArmorChangeEvent(event: EntityArmorChangeEvent) {}

    fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {}

    fun onEntityDamageEvent(event: EntityDamageEvent) {}

    fun onEntityDeathEvent(event: EntityDeathEvent) {}

    fun onCraftItemEvent(event: CraftItemEvent) {}

    fun onInventoryPickupItemEvent(event: InventoryPickupItemEvent) {}

    fun onInventoryTransactionEvent(event: InventoryTransactionEvent) {}

    fun onWeatherChangeEvent(event: WeatherChangeEvent) {}

    fun onEntityVehicleEnterEvent(event: EntityVehicleEnterEvent) {}

    fun onEntityLevelChangeEvent(event: EntityLevelChangeEvent) {}

    fun onEntityShootBowEvent(event: EntityShootBowEvent) {}

    fun onProjectileLaunchEvent(event: ProjectileLaunchEvent) {}

    fun onProjectileHitEvent(event: ProjectileHitEvent) {}

    fun onEntityTeleportEvent(event: EntityTeleportEvent) {}

    fun onBlockBurnEvent(event: BlockBurnEvent) {}

    fun onBlockFromToEvent(event: BlockFromToEvent) {}

    fun onBlockIgniteEvent(event: BlockIgniteEvent) {}

    fun onVehicleDestroyEvent(event: VehicleDestroyEvent) {}

    fun onPotionApplyEvent(event: PotionApplyEvent) {}

    fun onPlayerChunkRequestEvent(event: PlayerChunkRequestEvent) {}

    fun onEntityExplodeEvent(event: EntityExplodeEvent) {}

    fun onEntityExplosionPrimeEvent(event: EntityExplodeEvent) {}

    fun onLiquidFlowEvent(event: LiquidFlowEvent) {}

    fun onBlockFormEvent(event: BlockFormEvent) {}

    fun onLeavesDecayEvent(event: LeavesDecayEvent) {}






}
