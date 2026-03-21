package com.deezmods.unifiedui.extension.admin.essentials.enums;

public enum ExtensionId {
	COMMAND_ACTIONS("command.acctions"),
	PLAYER_LIST("player.list");

	public final String id;

	ExtensionId(String id) {
		this.id = "extension.admin.essentials.%s".formatted(id);
	}

	@Override
	public String toString() {
		return id;
	}
}
