package com.deezmods.unifiedui.extension.admin.essentials.enums;

public enum ExtensionId {
	COMMAND_ACTIONS("command.actions"),
	COMMAND_WHITELIST_PLAYER("command.whitelist.player"),
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
