package com.astral.collectors.listeners;

import com.astral.abstraction.persist.Collector;
import com.astral.collectors.Astral;
import com.astral.collectors.conf.files.Lang;
import com.astral.collectors.conf.files.Value;
import com.astral.collectors.persist.PhysicalCollectors;
import com.astral.collectors.persist.VirtualCollectors;

import com.astral.collectors.persist.impl.PhysicalCollector;
import com.astral.utils.Spigot;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PhysicalListener implements Listener {

    private final PhysicalCollectors physicalCollectors;
    private final VirtualCollectors virtualCollectors;

    @EventHandler
    public void playerInteraction(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Location location = event.getClickedBlock().getLocation();
        Optional<PhysicalCollector> collector = physicalCollectors.getCollector(location);

        if (!collector.isPresent())
            return;

         /*
            TODO:
              Open collector menu
              Link collector to player using cache
         */
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockPlace(BlockPlaceEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();

        if (physicalCollectors.getCollector(location).isPresent()) {
            event.setCancelled(true);
            player.sendMessage(Lang.COLLECTOR_ALREADY_PRESENT.communication());
            return;
        }

        if (virtualCollectors.getCollector(Board.getInstance().getFactionAt(new FLocation(location))).isPresent()) {
            event.setCancelled(true);
            player.sendMessage(Lang.COLLECTOR_ALREADY_PRESENT.communication());
            return;
        }

        physicalCollectors.createCollector(event.getBlock().getLocation());
        player.sendMessage(Lang.PLACED_COLLECTOR.communication(Spigot.toString(location, false)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreak(BlockBreakEvent event) {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();

        Optional<PhysicalCollector> collector = physicalCollectors.getCollector(location);

        if (!collector.isPresent() || !collector.get().getLocation().equals(location))
            return;

        event.setCancelled(true);
        block.setType(Material.AIR);
        location.getWorld().dropItemNaturally(location, Value.COLLECTOR_ITEM.getValue(ItemStack.class).clone());
        player.sendMessage(Lang.COLLECTOR_BROKEN.communication(Spigot.toString(location, false)));
    }

    public static void register(Astral.AstralAccess access, Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(
                new PhysicalListener(access.physicalCollectors(), access.virtualCollectors()), plugin);
    }

}
