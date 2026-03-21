package com.deezmods.unifiedui.extension.admin.essentials;

import com.deezmods.unifiedui.api.definitions.CommandDefinition;
import com.deezmods.unifiedui.api.definitions.FeatureDefinition;
import com.deezmods.unifiedui.extension.admin.essentials.commands.PluginCommands;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UuiExtension implements com.deezmods.unifiedui.api.UuiExtension {
	@Override
	public CompletableFuture<List<FeatureDefinition>> getFeatures(PlayerRef playerRef) {
		return CompletableFuture.completedFuture(List.of());
	}

	@Override
	public CompletableFuture<List<CommandDefinition>> getCommands(PlayerRef playerRef) {
		return CompletableFuture.completedFuture(List.of(
			PluginCommands.createActionListCommand(playerRef)
		));
	}

	@Override
	public CompletableFuture<Void> onPageClose() {
		return CompletableFuture.completedFuture(null);
	}
}
