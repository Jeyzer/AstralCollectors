package com.astral.collectors.conf.impl;

import lombok.Getter;
import lombok.SneakyThrows;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class Configurator {

    private final File file;
    private FileConfiguration config;

    /**
     * @param file File from which FileConfiguration will be loaded
     */
    public Configurator(File file) {
        this.file = file;
        reload();
    }

    // Loads the FileConfiguration using the cached File
    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves FileConfiguration into cached File
     */
    @SneakyThrows
    public void save() {
        config.save(file);
    }

}
