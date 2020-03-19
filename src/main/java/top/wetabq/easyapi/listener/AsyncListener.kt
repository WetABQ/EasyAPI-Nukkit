package top.wetabq.easyapi.listener

import cn.nukkit.event.block.BlockBreakEvent
import cn.nukkit.event.block.BlockPlaceEvent
import cn.nukkit.event.entity.EntityArmorChangeEvent
import cn.nukkit.event.entity.EntityDamageByEntityEvent
import cn.nukkit.event.entity.EntityDamageEvent
import cn.nukkit.event.entity.EntityDeathEvent
import cn.nukkit.event.inventory.CraftItemEvent
import cn.nukkit.event.inventory.InventoryPickupItemEvent
import cn.nukkit.event.inventory.InventoryTransactionEvent
import cn.nukkit.event.level.WeatherChangeEvent
import cn.nukkit.event.player.*

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

}