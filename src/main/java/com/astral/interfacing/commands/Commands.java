package com.astral.interfacing.commands;

import org.bukkit.command.CommandSender;

import java.util.Collection;

public interface Commands<T extends AffinityCommand> {

    Collection<T> getCommands();

    boolean registerCommand(T t);

    String[] execute(T t, CommandSender sender, String[] args);

}
