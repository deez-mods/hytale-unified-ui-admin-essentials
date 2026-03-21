package com.deezmods.unifiedui.extension.admintooling.commands;

import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class WhitelistCommands extends AbstractCommandCollection {
    public WhitelistCommands() {
        super("whitelist", "description");
        addSubCommand(new WhitelistEnableCommand());
        addSubCommand(new WhitelistDisableCommand());
    }
}

class WhitelistEnableCommand extends CommandBase {
    public WhitelistEnableCommand() {
        super("enable", "description");
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        CommandManager.get().handleCommand(commandContext.sender(), "whitelist enable");
    }
}

class WhitelistDisableCommand extends CommandBase {
    public WhitelistDisableCommand() {
        super("disable", "description");
    }

    @Override
    protected void executeSync(@NonNullDecl CommandContext commandContext) {
        CommandManager.get().handleCommand(commandContext.sender(), "whitelist disable");
    }
}