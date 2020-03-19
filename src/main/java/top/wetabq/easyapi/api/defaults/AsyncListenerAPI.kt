package top.wetabq.easyapi.api.defaults


import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
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
import top.wetabq.easyapi.EasyAPI
import top.wetabq.easyapi.api.SimpleIntegrateAPI
import top.wetabq.easyapi.listener.AsyncListener
import top.wetabq.easyapi.module.defaults.asyncTaskCallEvent

object AsyncListenerAPI : SimpleIntegrateAPI, Listener,  AsyncListener {

    private val asyncListenerList = arrayListOf<AsyncListener>()

    init {
        EasyAPI.server.pluginManager.registerEvents(this, EasyAPI.INSTANCE)
    }

    fun add(t: AsyncListener): AsyncListenerAPI {
        asyncListenerList.add(t)
        return this
    }

    fun remove(t: AsyncListener): AsyncListenerAPI {
        asyncListenerList.remove(t)
        return this
    }

    @EventHandler
    override fun onPlayerChatEvent(event: PlayerChatEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerChatEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerCommandPreprocessEvent(event: PlayerCommandPreprocessEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerCommandPreprocessEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerDeathEvent(event: PlayerDeathEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerDeathEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerBucketEmptyEvent(event: PlayerBucketEmptyEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerBucketEmptyEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerBucketFillEvent(event: PlayerBucketFillEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerBucketFillEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerDropItemEvent(event: PlayerDropItemEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerDropItemEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerEatFoodEvent(event: PlayerEatFoodEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerEatFoodEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerFoodLevelChangeEvent(event: PlayerFoodLevelChangeEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerFoodLevelChangeEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerFormRespondedEvent(event: PlayerFormRespondedEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerFormRespondedEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerGameModeChangeEvent(event: PlayerGameModeChangeEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerGameModeChangeEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerInteractEntityEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerInteractEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerItemHeldEvent(event: PlayerItemHeldEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerItemHeldEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerJoinEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerLoginEvent(event: PlayerLoginEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerLoginEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerMoveEvent(event: PlayerMoveEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerMoveEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerPreLoginEvent(event: PlayerPreLoginEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerPreLoginEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerQuitEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerRespawnEvent(event: PlayerRespawnEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerRespawnEvent(event) }
        }
    }

    @EventHandler
    override fun onPlayerTeleportEvent(event: PlayerTeleportEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onPlayerTeleportEvent(event) }
        }
    }

    @EventHandler
    override fun onBlockBreakEvent(event: BlockBreakEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onBlockBreakEvent(event) }
        }
    }

    @EventHandler
    override fun onBlockPlaceEvent(event: BlockPlaceEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onBlockPlaceEvent(event) }
        }
    }

    @EventHandler
    override fun onEntityArmorChangeEvent(event: EntityArmorChangeEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onEntityArmorChangeEvent(event) }
        }
    }

    @EventHandler
    override fun onEntityDamageByEntityEvent(event: EntityDamageByEntityEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onEntityDamageByEntityEvent(event) }
        }
    }

    @EventHandler
    override fun onEntityDamageEvent(event: EntityDamageEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onEntityDamageEvent(event) }
        }
    }

    @EventHandler
    override fun onEntityDeathEvent(event: EntityDeathEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onEntityDeathEvent(event) }
        }
    }

    @EventHandler
    override fun onCraftItemEvent(event: CraftItemEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onCraftItemEvent(event) }
        }
    }

    @EventHandler
    override fun onInventoryPickupItemEvent(event: InventoryPickupItemEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onInventoryPickupItemEvent(event) }
        }
    }

    @EventHandler
    override fun onInventoryTransactionEvent(event: InventoryTransactionEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onInventoryTransactionEvent(event) }
        }
    }

    @EventHandler
    override fun onWeatherChangeEvent(event: WeatherChangeEvent) {
        asyncTaskCallEvent(event, EasyAPI.INSTANCE) {
            asyncListenerList.forEach { it.onWeatherChangeEvent(event) }
        }
    }


}