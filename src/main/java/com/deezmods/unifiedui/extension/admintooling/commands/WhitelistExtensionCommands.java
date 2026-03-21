package com.deezmods.unifiedui.extension.admintooling.commands;

import com.deezmods.unifiedui.api.definitions.CommandDefinition;

public class WhitelistExtensionCommands
{
    public static CommandDefinition createWhitelistEnableCommand(){
        var builder = new CommandDefinition.Builder(
            "admin-tooling.whitelist.enable",
            "Whitelist: Enable",
            "Enable player whitelist"
        );
        builder.withRawCommand("/uui-at whitelist enable");
        return builder.build();
    }

    public static CommandDefinition createWhitelistDisableCommand(){
        var builder = new CommandDefinition.Builder(
            "admin-tooling.whitelist.disable",
            "Whitelist: Disable",
            "Disable player whitelist"
        );
        builder.withRawCommand("/uui-at whitelist disable");
        return builder.build();
    }
}
