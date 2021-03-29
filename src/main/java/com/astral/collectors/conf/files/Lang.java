package com.astral.collectors.conf.files;

import com.astral.collectors.conf.impl.Configurator;
import com.astral.utils.Spigot;

import lombok.AccessLevel;
import lombok.Getter;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.io.File;
import java.util.List;

@Getter
public enum Lang {

    COLLECTOR_BROKEN("collector-broken"),
    PLACED_COLLECTOR("placed-collector"),
    COLLECTOR_ALREADY_PRESENT("collector-already-present");

    private static boolean empty = true;
    private final String path;

    private @Getter(value = AccessLevel.NONE) String[] message;

    Lang(String path) {
        this.path = path;
    }

    public static void importFrom(File file) {
        Configurator configurator = new Configurator(file);

        for (Lang lang : Lang.values()) {
            Object msg = configurator.getConfig().get("Lang." + lang.getPath());
            lang.message = msg instanceof List
                    ? ((List<String>)msg).stream().map(Spigot::colors).toArray(String[]::new)
                    : new String[] { Spigot.colors(msg.toString()) };
        }

        empty = false;
    }

    public String[] communication() {
        return message.clone();
    }

    public String[] communication(Object... objects) {
        String[] msg = communication();

        for (int i = 0; i < msg.length; i++) {
            for (int j = 0; j < objects.length; j++) {
                msg[i] = msg[i].replace("{" + j + "}", objects[j].toString());
            }
        }

        return msg;
    }

    public BaseComponent[] complexCommunication(String runCommand, String hoverText, Object... objects) {
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hoverText));
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + runCommand);

        String[] message = communication(objects);
        int length = message.length;
        BaseComponent[] baseComponents = new BaseComponent[length];

        for (int i = 0; i < length; i++) {
            TextComponent component = new TextComponent(message[i]);
            component.setHoverEvent(hoverEvent);
            component.setClickEvent(clickEvent);

            baseComponents[i] = component;
        }

        return baseComponents;
    }
}
