package com.deezmods.unifiedui.extension.admin.essentials.enums;

public enum Permissions {
	COMMANDS("commands"),
	PLAYER_LIST("player.list");

	public final String id;

	Permissions(String id) {
		this.id = "com.deezmods.unifiedui.extension.admin.essentials.%s".formatted(id);
	}

	@Override
	public String toString() {
		return id;
	}
}
