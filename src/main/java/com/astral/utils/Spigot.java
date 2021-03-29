package com.astral.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Spigot {

    private Spigot() {
        throw new AssertionError("nope");
    }

    public static String colors(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static <E extends Collection<String>> List<String> colors(E e) {
        return e.stream().map(Spigot::colors).collect(Collectors.toList());
    }

    public static void log(Object... objects) {
        for (Object o : objects) Bukkit.getLogger().info(o.toString());
    }

    public static void validate(String message) {
        if (Bukkit.getServer() == null)
            throw new IllegalStateException(message);
    }

    public static String toString(Location location, boolean includeWorld) {
        Validate.notNull(location, "Location on toString convert cannot be null");
        return (includeWorld ? location.getWorld().getName() + ", " : "") + location.getX() + ", " + location.getY() + ", " + location.getZ();
    }

}
