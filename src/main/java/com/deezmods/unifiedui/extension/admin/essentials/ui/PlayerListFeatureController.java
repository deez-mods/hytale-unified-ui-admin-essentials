package com.deezmods.unifiedui.extension.admin.essentials.ui;

import com.deezmods.unifiedui.extension.admin.essentials.models.PlayerListItem;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;

import java.util.List;

public class PlayerListFeatureController {
	private static final String UI_FILE_PATH = "UnifiedUI-Extension-Admin-Essentials/PlayerListFeature.ui";

	private final String parentSelector;
	private final PlayerListFeatureItemController playerListFeatureItemController = new PlayerListFeatureItemController("#PlayerListContainer");

	public PlayerListFeatureController(String parentSelector) {
		this.parentSelector = parentSelector;
	}

	public void build(
		UICommandBuilder builder,
		List<PlayerListItem> allPlayers
	) {
		builder.clear(parentSelector);
		builder.append(parentSelector, UI_FILE_PATH);
		for (var index = 0; index < allPlayers.size(); index++) {
			playerListFeatureItemController.build(builder, allPlayers.get(index), index);
		}
	}
}
