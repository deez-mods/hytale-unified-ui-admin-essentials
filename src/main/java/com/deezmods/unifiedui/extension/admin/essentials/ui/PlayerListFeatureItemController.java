package com.deezmods.unifiedui.extension.admin.essentials.ui;

import com.deezmods.unifiedui.extension.admin.essentials.models.PlayerListItem;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;

public class PlayerListFeatureItemController {
	private static final String UI_FILE_PATH = "UnifiedUI-Extension-Admin-Essentials/PlayerListFeatureItem.ui";

	private final String parentSelector;

	public PlayerListFeatureItemController(String parentSelector) {
		this.parentSelector = parentSelector;
	}

	public void build(
		UICommandBuilder builder,
		PlayerListItem player,
		Integer index
	) {
		builder.append(parentSelector, UI_FILE_PATH);
		var selector = "%s[%s]".formatted(parentSelector, index);
		builder.set("%s #PlayerName.Text".formatted(selector), player.playerName);
		builder.set("%s #PlayerUuid.Text".formatted(selector), player.playerId.toString());
		builder.set("%s #ActionButtonBanAdd.Visible".formatted(selector), !player.isBanned);
		builder.set("%s #ActionButtonBanRemove.Visible".formatted(selector), player.isBanned);
		builder.set("%s #ActionButtonTpHere.Visible".formatted(selector), player.isOnline);
		builder.set("%s #ActionButtonTpThere.Visible".formatted(selector), player.isOnline);
		builder.set("%s #ActionButtonWhitelistAdd.Visible".formatted(selector), !player.isWhitelisted);
		builder.set("%s #ActionButtonWhitelistRemove.Visible".formatted(selector), player.isWhitelisted);
		builder.set("%s #ActionButtonKick.Visible".formatted(selector), player.isOnline);
	}
}
