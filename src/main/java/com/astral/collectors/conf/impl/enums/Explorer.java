package com.astral.collectors.conf.impl.enums;

import lombok.RequiredArgsConstructor;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

@RequiredArgsConstructor
public enum Explorer implements BiConsumer<File, Plugin> {

    DIRECTORY((file, plugin) -> file.mkdir()),

    RESOURCE((file, plugin) -> {
        if (file.exists()) return;
        plugin.saveResource(file.getName(), false);
    }),

    NEW_FILE((file, plugin) -> {
        try {
            file.createNewFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
    });

    private final BiConsumer<File, Plugin> consumer;

    @Override
    public void accept(File file, Plugin plugin) {
        consumer.accept(file, plugin);
    }
}
