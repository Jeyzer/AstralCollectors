package com.astral.collectors.conf;

import com.astral.collectors.conf.files.Lang;
import com.astral.collectors.conf.files.Value;
import com.astral.collectors.conf.impl.Configurator;
import com.astral.collectors.conf.impl.enums.Config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public final class Conf {

    private final Plugin plugin;
    private @Getter Configurator collectors;

    public File makeFile(Config config) {
        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            return null;
        }

        File file = new File(dataFolder, config.getName() + ".yml");
        config.getExplorer().accept(file, plugin);

        switch (config) {
            case COLLECTORS:
                collectors = new Configurator(file);
            case LANG:
                Lang.importFrom(file);
                break;
            case SETTINGS:
                Value.importFrom(file);
        }

        return file;
    }

}
