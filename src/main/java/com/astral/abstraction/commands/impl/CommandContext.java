package com.astral.abstraction.commands.impl;

import lombok.Getter;

@Getter
public class CommandContext implements Cloneable {

    private String[] playerRequired, permissionDenied, invalidArguments;

    public void playerRequired(String... str) {
        playerRequired = str;
    }

    public void permissionDenied(String... str) {
        permissionDenied = str;
    }

    public void invalidArguments(String... str) {
        invalidArguments = str;
    }

    public CommandContext clone() {
        try {
            return (CommandContext) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
