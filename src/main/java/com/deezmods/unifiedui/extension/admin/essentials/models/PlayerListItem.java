package com.deezmods.unifiedui.extension.admin.essentials.models;

import java.util.UUID;

public class PlayerListItem {
	public final UUID playerId;
	public final String playerName;
	public Boolean isOnline;
	public Boolean isBanned;
	public Boolean isWhitelisted;

	public PlayerListItem(
		UUID playerId,
		String playerName,
		Boolean isOnline,
		Boolean isBanned,
		Boolean isWhitelisted
	) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.isOnline = isOnline;
		this.isBanned = isBanned;
		this.isWhitelisted = isWhitelisted;
	}
}
