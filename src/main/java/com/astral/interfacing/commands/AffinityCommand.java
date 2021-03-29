package com.astral.interfacing.commands;

import net.affinity.abstraction.interfacing.Nominable;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.Collection;

public interface AffinityCommand extends CommandExecutor, Nominable {

    Commands<? extends AffinityCommand> getHandler();

    Collection<Method> getSubCommands();

    int getRequiredLength();

    boolean isPlayerRequired();

    String[] getPermissions();

    String[] execute(CommandSender sender, String[] args);

}
