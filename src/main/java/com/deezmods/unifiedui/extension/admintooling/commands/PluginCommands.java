package com.deezmods.unifiedui.extension.admintooling.commands;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PluginCommands extends AbstractCommandCollection {
    public PluginCommands() {
        super("uui-at", "description");
        addSubCommand(new WhitelistCommands());
    }
}
