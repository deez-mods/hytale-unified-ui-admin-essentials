package com.deezmods.unifiedui.extension.admin.essentials.enums;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public enum PlayerListActions {
	PLAYER_BAN("player.ban"),
	PLAYER_UNBAN("player.unban");

	public final String id;

	PlayerListActions(String id) {
		this.id = id;
	}

	public static @Nullable PlayerListActions fromId(String id) {
		return Arrays
			.stream(PlayerListActions.values())
			.filter(x -> Objects.equals(x.id, id))
			.findFirst()
			.orElse(null);
	}

	@Override
	public String toString() {
		return id;
	}
}
