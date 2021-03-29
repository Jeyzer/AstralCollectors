package com.astral.interfacing;

import net.md_5.bungee.api.chat.BaseComponent;

public interface Communicative {

    String[] communication();
    String[] communication(Object... objects);

    BaseComponent[] complexCommunication(String run, String hover, Object... objects);

}
