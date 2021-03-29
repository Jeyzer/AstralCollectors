package com.astral.collectors.menus;

import com.astral.abstraction.interfacing.menus.Button;
import com.astral.abstraction.interfacing.menus.Menu;

import com.astral.collectors.conf.files.Value;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.Map;

public class Menus {

    protected final Plugin plugin;

    private final Map<Player, Menu> menusMap = Maps.newConcurrentMap();

    @Getter
    private final BukkitTask bukkitTask;

    public Menus(Plugin plugin) {
        this.plugin = plugin;

        bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () ->
                    menusMap.forEach((k, v) -> {
                        v.build(false);
                        k.updateInventory();
                    }), 20L, Value.MENUS_UPDATE_INTERVAL.getValue(Integer.class));

        Bukkit.getPluginManager().registerEvents(new MenusListener(), plugin);
    }

    private void save(@NonNull Player player, @NonNull Menu menu) {
        menusMap.put(player, menu);
    }

    @Nullable
    public Menu getOpenMenu(@NonNull Player player) {
        return menusMap.get(player);
    }

    private void onClose(HumanEntity humanEntity) {
        menusMap.remove(humanEntity);
    }

    public void showMenu(Player player, Menu menu) {
        menu.build(true);
        player.openInventory(menu.getBuilt());
        save(player, menu);
    }

    @RequiredArgsConstructor
    private class MenusListener implements Listener {

        @EventHandler
        void inventoryClick(InventoryClickEvent event) {
            Player player = (Player) event.getWhoClicked();

            Menu menu = getOpenMenu(player);
            if (event.getCurrentItem() == null || menu == null) return;
            event.setCancelled(true);

            Button button = menu.getButtons()[event.getSlot()];
            if (button == null || button.getMaterial() == Material.AIR) return;

            Menu openable = button.apply(player, event.getClick());
            if (openable == null) return;
            showMenu(player, openable);
        }

        @EventHandler
        void inventoryClose(InventoryCloseEvent event) {
            onClose(event.getPlayer());
        }

    }

}
