package com.astral.collectors;

import com.astral.collectors.conf.Conf;
import com.astral.collectors.conf.files.Value;
import com.astral.collectors.conf.impl.enums.Config;
import com.astral.collectors.listeners.BlockGrowListener;
import com.astral.collectors.listeners.PhysicalListener;
import com.astral.collectors.listeners.EntityDeathListener;
import com.astral.collectors.menus.Menus;
import com.astral.collectors.persist.PhysicalCollectors;
import com.astral.collectors.persist.VirtualCollectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Astral extends JavaPlugin {

    private static AstralAccess access;
    protected Conf conf;

    protected PhysicalCollectors physicalCollectors;
    protected VirtualCollectors virtualCollectors;

    protected Menus menus;

    public void onEnable() {
        access = new AstralAccess();
        setupFiles();
        physicalCollectors = new PhysicalCollectors();
        virtualCollectors = new VirtualCollectors();
        menus = new Menus(this);
        registerListeners();
    }

    @SneakyThrows
    private void registerListeners() {
        AstralAccess access = access();

        PhysicalListener.register(access, this);
        EntityDeathListener.register(access, this);

        if (!access.isWhitelisted(new ItemStack(Material.CACTUS)))
            return;

        BlockGrowListener.register(access, this);
    }

    public void onDisable() {
        access = null;
    }

    private void setupFiles() {
        conf = new Conf(this);

        for (Config config : Config.values())
            conf.makeFile(config);
    }

    public static AstralAccess access() {
        return access;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public class AstralAccess {

        public PhysicalCollectors physicalCollectors()  { return physicalCollectors; }
        public VirtualCollectors   virtualCollectors()  { return virtualCollectors;  }

        public boolean isWhitelisted(LivingEntity livingEntity) {
            Validate.notNull(livingEntity, "LivingEntity in whitelist check cannot be null");
            return Value.AFFLICTED_ENTITIES.getValue(List.class).contains(livingEntity.getType().toString());
        }

        public boolean isWhitelisted(ItemStack itemStack) {
            Validate.notNull(itemStack, "ItemStack in whitelist check cannot be null");
            return Value.AFFLICTED_ITEMS.getValue(List.class).contains(itemStack.getType().toString() + ", " + itemStack.getDurability());
        }

    }

}
