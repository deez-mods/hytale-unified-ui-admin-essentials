package com.deezmods.unifiedui.extension.admin.essentials.enums;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public enum PlayerListActions {
	BAN_ADD("ban.add"),
	BAN_REMOVE("ban.remove"),
	TP_HERE("tp.here"),
	TP_THERE("tp.there"),
	WHITELIST_ADD("whitelist.add"),
	WHITELIST_REMOVE("whitelist.remove"),
	KICK("kick");

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
