package com.deezmods.unifiedui.extension.admin.essentials.models;

import java.util.UUID;

public record PlayerListItem(
	UUID playerId,
	String playerName,
	Boolean isBanned
) {
}
