package com.astral.abstraction.commands;

import com.astral.abstraction.commands.impl.CommandContext;
import com.astral.annotations.SubCommand;
import com.astral.interfacing.commands.AffinityCommand;
import com.astral.interfacing.commands.Commands;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public abstract class AbstractCommand implements AffinityCommand {

    private final Commands<AbstractCommand> handler;

    @Setter(value = AccessLevel.NONE)
    private @NonNull String name;

    private String[] permissions;
    private CommandContext commandContext = new CommandContext();
    private int requiredLength;
    private boolean playerRequired;

    public Collection<Method> getSubCommands() {
        List<Method> list = new ArrayList<>();
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                list.add(method);
            }
        }
        return list;
    }

    public void setPermissions(String... strings) {
        permissions = strings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        String[] msg = handler.execute(this, sender, args);

        if (msg != null) {
            sender.sendMessage(msg);
            return true;
        }

        return false;
    }

    public void setCommandContext(CommandContext commandContext) {
        this.commandContext = commandContext.clone();
    }

}
