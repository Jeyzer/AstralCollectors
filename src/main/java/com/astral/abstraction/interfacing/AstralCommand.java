package com.astral.abstraction.interfacing;

import com.astral.interfacing.commands.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.Collection;

public interface AstralCommand extends CommandExecutor, Nominable {

    Commands<? extends AstralCommand> getHandler();

    Collection<Method> getSubCommands();

    int getRequiredLength();

    boolean isPlayerRequired();

    String[] getPermissions();

    String[] execute(CommandSender sender, String[] args);

}