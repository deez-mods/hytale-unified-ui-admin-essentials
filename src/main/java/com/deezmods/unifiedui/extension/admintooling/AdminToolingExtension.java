package com.deezmods.unifiedui.extension.admintooling;

import com.deezmods.unifiedui.api.UuiExtension;
import com.deezmods.unifiedui.api.definitions.CommandDefinition;
import com.deezmods.unifiedui.api.definitions.FeatureDefinition;
import com.deezmods.unifiedui.extension.admintooling.commands.WhitelistExtensionCommands;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AdminToolingExtension implements UuiExtension {
    @Override
    public CompletableFuture<List<FeatureDefinition>> getFeatures(PlayerRef playerRef) {
        return CompletableFuture.completedFuture(List.of());
    }

    @Override
    public CompletableFuture<List<CommandDefinition>> getCommands(PlayerRef playerRef) {
        List<CommandDefinition> commandDefinitions = new ArrayList<>();
        commandDefinitions.add(WhitelistExtensionCommands.createWhitelistEnableCommand());
        commandDefinitions.add(WhitelistExtensionCommands.createWhitelistDisableCommand());
        return CompletableFuture.completedFuture(commandDefinitions);
    }

    @Override
    public CompletableFuture<Void> onPageClose() {
        return CompletableFuture.completedFuture(null);
    }
}
