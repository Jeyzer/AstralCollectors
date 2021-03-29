package com.astral.collectors.listeners;

import com.astral.abstraction.persist.Collector;
import com.astral.collectors.Astral;
import com.astral.collectors.persist.PhysicalCollectors;
import com.astral.collectors.persist.VirtualCollectors;
import com.astral.collectors.persist.impl.PhysicalCollector;
import com.astral.collectors.persist.impl.VirtualCollector;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockGrowListener implements Listener {

    private final PhysicalCollectors physicalCollectors;
    private final VirtualCollectors virtualCollectors;

    @EventHandler
    public void blockGrow(BlockGrowEvent event) {
        if (event.getBlock().getType() != Material.CACTUS)
            return;

        Location location = event.getBlock().getLocation();
        Optional<PhysicalCollector> physical = physicalCollectors.getCollector(location);

        if (physical.isPresent()) {
            event.setCancelled(true);
            handle(physical.get());
            return;
        }

        Optional<VirtualCollector> virtual = virtualCollectors.getCollector(Board.getInstance().getFactionAt(new FLocation(location)));

        if (!virtual.isPresent())
            return;

        event.setCancelled(true);
        handle(virtual.get());
    }

    private static void handle(Collector collector) {
        collector.getAmount(Material.CACTUS, (short) 0).increment();
    }

    public static void register(Astral.AstralAccess access, Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(
                new BlockGrowListener(access.physicalCollectors(), access.virtualCollectors()), plugin);
    }

}
