package com.astral.collectors.listeners;

import com.astral.abstraction.persist.Collector;
import com.astral.collectors.Astral;
import com.astral.collectors.conf.files.Value;
import com.astral.collectors.persist.PhysicalCollectors;
import com.astral.collectors.persist.VirtualCollectors;
import com.astral.collectors.persist.impl.PhysicalCollector;
import com.astral.collectors.persist.impl.VirtualCollector;
import com.astral.collectors.persist.serialization.ItemData;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityDeathListener implements Listener {

    private final PhysicalCollectors physicalCollectors;
    private final VirtualCollectors virtualCollectors;

    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();

        if (!Value.AFFLICTED_ENTITIES.getValue(List.class).contains(entity.getType().toString()))
            return;

        Collector collector;
        Optional<PhysicalCollector> physical = physicalCollectors.getCollector(location);

        if (physical.isPresent())
            collector = physical.get();
        else {
            Optional<VirtualCollector> virtual = virtualCollectors.getCollector(Board.getInstance().getFactionAt(new FLocation(location)));

            if (!virtual.isPresent())
                return;

            collector = virtual.get();
        }

        Iterator<ItemStack> iterator = event.getDrops().iterator();
        List<String> whitelist = Value.AFFLICTED_ITEMS.getValue(List.class);

        while (iterator.hasNext()) {
            ItemStack next = iterator.next();
            ItemData itemData = ItemData.direct(next);

            if (!whitelist.contains(itemData.toString()))
                continue;

            collector.getAmount(itemData).add(next.getAmount());
            iterator.remove();
        }
    }

    public static void register(Astral.AstralAccess access, Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(
                new EntityDeathListener(access.physicalCollectors(), access.virtualCollectors()), plugin);
    }

}
