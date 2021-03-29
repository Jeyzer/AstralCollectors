package com.astral.collectors.conf.files;

import com.astral.collectors.conf.impl.Configurator;
import com.astral.utils.ItemBuilder;
import com.astral.utils.Spigot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang.ClassUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.stream.Collectors;

@RequiredArgsConstructor @Getter
public enum Value {

    COLLECTOR_ITEM("collector-item"),
    AFFLICTED_ITEMS("Afflicts.items"),
    AFFLICTED_ENTITIES("Afflicts.entities"),
    MENUS_UPDATE_INTERVAL("Intervals.menus-update");

    private static boolean empty = true;
    private final String path;
    private @Getter(value = AccessLevel.NONE) Object value;

    public <E> E getValue(Class<E> elem) {
        if (elem.isAssignableFrom(Material.class)) {
            if (!(value instanceof String))
                throw new ClassCastException("Cannot cast " + value.getClass().getTypeName() + " to " + elem.getTypeName());

            return elem.cast(Material.valueOf(value.toString()));
        }

        return (E) (elem.isPrimitive() ? ClassUtils.primitiveToWrapper(elem) : elem);
    }

    public static void importFrom(File file) {
        Configurator configurator = new Configurator(file);
        FileConfiguration config = configurator.getConfig();

        for (Value value : Value.values()) {
            if (value == COLLECTOR_ITEM) {
                value.value =
                        ItemBuilder
                            .create()
                            .asMaterial(Material.NOTE_BLOCK)
                            .nominated(Spigot.colors(config.getString("collector-item.display-name")))
                            .lore(Spigot.colors(config.getStringList("collector-item-lore")))
                            .build();
                continue;
            }

            value.value = config.get(value.path);
        }

        empty = false;
    }
}
