package com.astral.collectors.conf.impl.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @Getter
public enum Config {

    COLLECTORS("collectors", Explorer.NEW_FILE),
    LANG("lang", Explorer.RESOURCE),
    SETTINGS("settings", Explorer.RESOURCE);

    private final String name;
    private final Explorer explorer;

}
